package de.pecus.api.services.usuarios.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.pecus.api.constant.GeneralConstants;
import de.pecus.api.constant.UsuariosDataConstants;
import de.pecus.api.entities.UsuarioDO;
import de.pecus.api.enums.WildcardTypeEnum;
import de.pecus.api.error.GeneralBusinessErrors;
import de.pecus.api.error.UsuarioBusinessErrors;
import de.pecus.api.exception.PecusException;
//import de.pecus.api.log.SmartLogger;
//import de.pecus.api.log.SmartLoggerFactory;
import de.pecus.api.repositories.usuarios.UsuarioRepository;
import de.pecus.api.security.services.PasswordService;
import de.pecus.api.services.usuarios.UsuarioService;
import de.pecus.api.util.CriteriaUtil;
import de.pecus.api.util.ResponseUtil;
import de.pecus.api.util.ServiceUtil;
import de.pecus.api.util.StringUtil;
import de.pecus.api.util.ValidatorUtil;
import de.pecus.api.vo.RequestVO;
import de.pecus.api.vo.ResponseVO;
import de.pecus.api.vo.usuarios.CrearUsuarioRequestVO;
import de.pecus.api.vo.usuarios.DeleteUsuarioRequestVO;
import de.pecus.api.vo.usuarios.FindDetailUsuarioRequestVO;
import de.pecus.api.vo.usuarios.FindDetailUsuarioResponseVO;
import de.pecus.api.vo.usuarios.FindListUsuarioRequestVO;
import de.pecus.api.vo.usuarios.FindListUsuarioResponseVO;
import de.pecus.api.vo.usuarios.UpdateUsuarioRequestVO;

/**
 * Clase de logica de negocio para administracionde Tipos de Domicilio
 * 
 * @author Luis Enrique Sanchez Santiago
 *
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

	/** Logger */
	//private static final SmartLogger LOGGER = SmartLoggerFactory.getLogger(UsuarioServiceImpl.class);

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private PasswordService passwordService;

	@Autowired
	private EntityManager em;
	

	/**
	 * Metodo encargado de validar la existencia del usuario por su identificador
	 * 
	 * @param idUsuario
	 */
	public UsuarioDO exists(Long idUsuario, String idioma) {

		// Validacion de datos de entrada
		if (ValidatorUtil.isNullOrZero(idUsuario)) {
			throw new PecusException(UsuarioBusinessErrors.REQUIRED_USUARIO_ERROR, idioma);
		}

		// Consulta
		UsuarioDO usuario = usuarioRepository.findByIdAndActive(idUsuario, Boolean.TRUE);

		// Validacion de existencia
		if (ValidatorUtil.isNull(usuario)) {
			// Genera error
			throw new PecusException(UsuarioBusinessErrors.NOT_FOUND_USUARIO_ERROR, idioma);
		}
		return usuario;
	}

	@Override
	public ResponseVO<List<FindListUsuarioResponseVO>> findListUsuario(RequestVO<FindListUsuarioRequestVO> request) {
		ResponseVO<List<FindListUsuarioResponseVO>> response  = new ResponseVO<List<FindListUsuarioResponseVO>>();
		List<UsuarioDO> usuarioDOs = null;
		
		if(validaParametrosFindListUsuario(request, response)) {
			String orderbyCriteria = request.getOrderBy();
			String ordertypeCriteria = request.getOrderType();
			
			String orderBy = ValidatorUtil.isNullOrEmpty(orderbyCriteria) ? "id" : orderbyCriteria;
			Direction orderType = ValidatorUtil.isNullOrEmpty(ordertypeCriteria) || ordertypeCriteria.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
			
			Integer size = ValidatorUtil.isNullOrZero(request.getSize()) ? 10 : request.getSize();
			Integer page = ValidatorUtil.isNullOrZero(request.getPage()) ? 1 : request.getPage();
			
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<UsuarioDO> cq = cb.createQuery(UsuarioDO.class);
			CriteriaQuery<Long> cqCount = cb.createQuery(Long.class);
			
			Root<UsuarioDO> usuarioRoot = cq.from(UsuarioDO.class);
			Root<UsuarioDO> usuarioRootCount = cqCount.from(UsuarioDO.class);
			
			List<Predicate> predicates = buildPredicates(request, cb, usuarioRoot);
			List<Predicate> predicatesCount = buildPredicates(request, cb, usuarioRootCount);
			
			// Validamos el tipo de ordenamiento y se genera la lista de orders
			List<Order> orders = new ArrayList<Order>();
			if (orderType.isDescending()) {
				orders.add(cb.desc(usuarioRoot.get(orderBy)));
			}
			
			if (orderType.isAscending()) {
				orders.add(cb.asc(usuarioRoot.get(orderBy)));
			}
			
			// Armamos query para count de paginación
			cqCount.select(cb.count(usuarioRootCount)).where(predicatesCount.toArray(new Predicate[] {}));
			Long count = em.createQuery(cqCount).getSingleResult();
			
			// Armamos query principal
			cq.select(usuarioRoot).where(predicates.toArray(new Predicate[] {})).orderBy(orders);
			
			TypedQuery<UsuarioDO> query = em.createQuery(cq);
			
			// Agregamos la paginacion
			query.setFirstResult(((page - 1) * size));
			query.setMaxResults(size);
			
			usuarioDOs = query.getResultList();
			
			if (ValidatorUtil.isNullOrEmpty(usuarioDOs)) {
				ResponseUtil.addError(request, response, UsuarioBusinessErrors.NOT_FOUND_USUARIO_ERROR);
			} else {
				response.setTotalRows(count);
				response.setSuccess(Boolean.TRUE);
				response.setData(transformFindListUsuarioDO(usuarioDOs));
			}
		}
		
		return response;
	}
	
	private Boolean validaParametrosFindListUsuario(RequestVO<FindListUsuarioRequestVO> request, ResponseVO<List<FindListUsuarioResponseVO>> response) {
		if(ValidatorUtil.isNull(request.getParameters())) {
			ResponseUtil.addError(request, response, GeneralBusinessErrors.REQUIRED_PARAMETERS_ERROR);
		}
		
		return ValidatorUtil.isSuccessfulResponse(response);
	}
	
    private List<Predicate> buildPredicates(RequestVO<FindListUsuarioRequestVO> requestVO, CriteriaBuilder cb,
			Root<UsuarioDO> usuarioRoot) {
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (!ValidatorUtil.isNullOrEmpty(requestVO.getParameters().getEmail())) {
			Predicate usuarioIdEmailPredicate = cb.like(usuarioRoot.get("userIdEmail"),
					CriteriaUtil.validateNullLike(requestVO.getParameters().getEmail(), WildcardTypeEnum.BOTH_SIDES));
			predicates.add(usuarioIdEmailPredicate);
		}
		if (!ValidatorUtil.isNullOrEmpty(requestVO.getParameters().getTelefono())) {
			Predicate usuarioIdMobilePredicate = cb.like(usuarioRoot.get("userIdMobileNumber"),
					CriteriaUtil.validateNullLike(requestVO.getParameters().getTelefono(), WildcardTypeEnum.BOTH_SIDES));
			predicates.add(usuarioIdMobilePredicate);
		}
		
		Predicate activePredicate = cb.equal(usuarioRoot.get("active"), GeneralConstants.ONE);
		predicates.add(activePredicate);
		
		return predicates;
	}
    
    private List<FindListUsuarioResponseVO> transformFindListUsuarioDO(List<UsuarioDO> usuarioDOs){
    	List<FindListUsuarioResponseVO> findListUsuarioResponseVOs = new ArrayList<FindListUsuarioResponseVO>();
    	for(UsuarioDO usuarioDO : usuarioDOs) {
    		FindListUsuarioResponseVO findListUsuarioResponseVO = new FindListUsuarioResponseVO();
    		findListUsuarioResponseVO.setId(usuarioDO.getId());
    		findListUsuarioResponseVO.setImagenPerfil(usuarioDO.getImagenPerfil());
    		findListUsuarioResponseVO.setLadaPais(usuarioDO.getLadaPais());
    		findListUsuarioResponseVO.setNotificationsEnabled(Boolean.valueOf(usuarioDO.getNotificationsEnabled().toString()));
    		findListUsuarioResponseVO.setTerminosAceptados(usuarioDO.getTerminosAceptados());
    		findListUsuarioResponseVO.setUserIdEmail(usuarioDO.getUserIdEmail());
    		findListUsuarioResponseVO.setUserIdMobileDevice(usuarioDO.getUserIdMobileDevice());
    		findListUsuarioResponseVO.setUserIdMobileNumber(usuarioDO.getUserIdMobileNumber());
    		findListUsuarioResponseVOs.add(findListUsuarioResponseVO);
    	}
    	
    	return findListUsuarioResponseVOs;
    }

	@Transactional
	@Override
	public ResponseVO<Boolean> deleteUsuario(RequestVO<DeleteUsuarioRequestVO> request) {
		ResponseVO<Boolean> response = new ResponseVO<Boolean>();

		if (validaParametrosDeleteUsuario(request, response)) {
			// Consultamos el usuario y validamos su existencia
			UsuarioDO usuarioDO = exists(request.getParameters().getIdUsuario(), GeneralConstants.IDIOMA_ES_MX);
			// Deshabilitamos al usuario por medio de la bandera de active
			ServiceUtil.setDisabledEntity(usuarioDO, request.getToken());
			// Actualizamos el usuario
			usuarioRepository.saveAndFlush(usuarioDO);
			response.setData(Boolean.TRUE);
			response.setSuccess(Boolean.TRUE);
		}

		return response;
	}

	private Boolean validaParametrosDeleteUsuario(RequestVO<DeleteUsuarioRequestVO> request,
			ResponseVO<Boolean> response) {

		if (ValidatorUtil.isNull(request.getParameters())) {
			ResponseUtil.addError(request, response, GeneralBusinessErrors.REQUIRED_PARAMETERS_ERROR);
		} else {
			if (ValidatorUtil.isNullOrZero(request.getParameters().getIdUsuario())) {
				ResponseUtil.addError(request, response, UsuarioBusinessErrors.REQUIRED_ID_USUARIO);
			}
		}

		return ValidatorUtil.isSuccessfulResponse(response);
	}

	@Transactional
	@Override
	public ResponseVO<Long> crearUsuario(RequestVO<CrearUsuarioRequestVO> request) {
		
		ResponseVO<Long> response = new ResponseVO<Long>();
		
		if(validaParametrosCrearUsuario(request, response)) {
			// Seteamos la información del usuario
			UsuarioDO usuarioDO = new UsuarioDO();
			usuarioDO.setUserIdEmail(request.getParameters().getEmail());
			usuarioDO.setUserIdMobileNumber(request.getParameters().getTelefono());
			usuarioDO.setLadaPais(UsuariosDataConstants.LADA_MEXICO);
			usuarioDO.setPasswordStatus(UsuariosDataConstants.ESTATUS_PASSWORD_ACTUALIZADO);
			usuarioDO.setPassword(passwordService.encryptPasswordSHA256(request.getParameters().getPassword()));
			usuarioDO.setNotificationsEnabled(request.getParameters().getNotificaciones());
			usuarioDO.setImagenPerfil(request.getParameters().getImagenPerfil());
			ServiceUtil.setAuditFields(usuarioDO, request.getToken());
			usuarioDO = usuarioRepository.saveAndFlush(usuarioDO);
			response.setData(usuarioDO.getId());
			response.setSuccess(Boolean.TRUE);
		}

		return response;
	}
	
	private Boolean validaParametrosCrearUsuario(RequestVO<CrearUsuarioRequestVO> request,
			ResponseVO<Long> response) {

		if (ValidatorUtil.isNull(request.getParameters())) {
			ResponseUtil.addError(request, response, GeneralBusinessErrors.REQUIRED_PARAMETERS_ERROR);
		} else {
			// Valida el correo electronico registrado.
			if (!ValidatorUtil.isNullOrEmpty(request.getParameters().getEmail())) {

				// Validacion de formato de correo electronico valido
				if (!StringUtil.isValidEmail(request.getParameters().getEmail())) {
					ResponseUtil.addError(request, response, UsuarioBusinessErrors.INVALID_EMAIL_FORMAT_ERROR);
					//LOGGER.debug(request, UsuarioBusinessErrors.INVALID_EMAIL_FORMAT_ERROR);
				}

				// Validacion duplicidad de correo electronico
				UsuarioDO usuarioDO = usuarioRepository.findByUserIdEmail(request.getParameters().getEmail());
				if (!ValidatorUtil.isNull(usuarioDO)) {
					ResponseUtil.addError(request, response, UsuarioBusinessErrors.DUPLICATED_USER_ID_EMAIL_ERROR);
					//LOGGER.debug(request, UsuarioBusinessErrors.DUPLICATED_USER_ID_EMAIL_ERROR);
				}
			}

			// Valida que el numero movil sea informado y este no se haya registrado
			if (StringUtil.isNullOrEmpty(request.getParameters().getTelefono())) {
				ResponseUtil.addError(request, response, UsuarioBusinessErrors.REQUIRED_USER_ID_MOBILE_NUMBER_ERROR);
				//LOGGER.debug(request, UsuarioBusinessErrors.REQUIRED_USER_ID_MOBILE_NUMBER_ERROR);
			} else {
				// Validacion duplicidad
				String numeroTelefonico = CriteriaUtil.validateNullLike(request.getParameters().getTelefono().trim(),
						WildcardTypeEnum.BOTH_SIDES);
				UsuarioDO usuarioDO = usuarioRepository.findByMobileNumberAndLadaPais(UsuariosDataConstants.LADA_MEXICO,
						numeroTelefonico);
				if (!ValidatorUtil.isNull(usuarioDO)) {
					ResponseUtil.addError(request, response, UsuarioBusinessErrors.DUPLICATED_USER_MOBILE_NUMBER_ERROR);
					//LOGGER.debug(request, UsuarioBusinessErrors.DUPLICATED_USER_MOBILE_NUMBER_ERROR);
				}
			}
			
		}

		return ValidatorUtil.isSuccessfulResponse(response);
	}

	@Transactional
	@Override
	public ResponseVO<Long> updateUsuario(RequestVO<UpdateUsuarioRequestVO> request) {

		ResponseVO<Long> response = new ResponseVO<Long>();

		if (validaParametrosUpdateUsuario(request, response)) {
			// Consultamos el usuario y validamos su existencia
			UsuarioDO usuarioDO = exists(request.getParameters().getId(), GeneralConstants.IDIOMA_ES_MX);
			if (!ValidatorUtil.isNullOrEmpty(request.getParameters().getImagenPerfil())) {
				usuarioDO.setImagenPerfil(request.getParameters().getImagenPerfil());
			}
			if (!ValidatorUtil.isNullOrEmpty(request.getParameters().getEmail())) {
				usuarioDO.setUserIdEmail(request.getParameters().getEmail());
			}
			if (!ValidatorUtil.isNullOrEmpty(request.getParameters().getTelefono())) {
				usuarioDO.setUserIdMobileNumber(request.getParameters().getTelefono());
			}
			if (!ValidatorUtil.isNullOrEmpty(request.getParameters().getPassword())) {
				usuarioDO.setPassword(passwordService.encryptPasswordSHA256(request.getParameters().getPassword()));
			}
			if (!ValidatorUtil.isNull(request.getParameters().getNotificaciones())) {
				usuarioDO.setNotificationsEnabled(request.getParameters().getNotificaciones());
			}
			ServiceUtil.setAuditFields(usuarioDO, request.getToken());
			// Actualizamos el usuario
			usuarioRepository.saveAndFlush(usuarioDO);
			response.setData(usuarioDO.getId());
			response.setSuccess(Boolean.TRUE);
		}

		return response;
	}

	private Boolean validaParametrosUpdateUsuario(RequestVO<UpdateUsuarioRequestVO> request,
			ResponseVO<Long> response) {

		if (ValidatorUtil.isNull(request.getParameters())) {
			ResponseUtil.addError(request, response, GeneralBusinessErrors.REQUIRED_PARAMETERS_ERROR);
		} else {
			if (ValidatorUtil.isNullOrZero(request.getParameters().getId())) {
				ResponseUtil.addError(request, response, UsuarioBusinessErrors.REQUIRED_ID_USUARIO);
			}else {
				
				// Buscamos el usuario original
				UsuarioDO originalUsuarioDO = usuarioRepository.findByIdAndActive(request.getParameters().getId(), Boolean.TRUE);
				
				// Valida el correo electronico registrado.
				if (!ValidatorUtil.isNullOrEmpty(request.getParameters().getEmail())) {
					
					// Validacion de formato de correo electronico valido
					if (!StringUtil.isValidEmail(request.getParameters().getEmail())) {
						ResponseUtil.addError(request, response, UsuarioBusinessErrors.INVALID_EMAIL_FORMAT_ERROR);
						//LOGGER.debug(request, UsuarioBusinessErrors.INVALID_EMAIL_FORMAT_ERROR);
					}
					
					// Validacion duplicidad de correo electronico
					UsuarioDO usuarioDO = usuarioRepository.findByUserIdEmail(request.getParameters().getEmail());
					if (!ValidatorUtil.isNull(usuarioDO)
							&& !originalUsuarioDO.getUserIdEmail().equals(request.getParameters().getEmail())) {
						ResponseUtil.addError(request, response, UsuarioBusinessErrors.DUPLICATED_USER_ID_EMAIL_ERROR);
						//LOGGER.debug(request, UsuarioBusinessErrors.DUPLICATED_USER_ID_EMAIL_ERROR);
					}
				}
				
				// Valida que el numero movil sea informado y este no se haya registrado
				if (StringUtil.isNullOrEmpty(request.getParameters().getTelefono())) {
					ResponseUtil.addError(request, response, UsuarioBusinessErrors.REQUIRED_USER_ID_MOBILE_NUMBER_ERROR);
					//LOGGER.debug(request, UsuarioBusinessErrors.REQUIRED_USER_ID_MOBILE_NUMBER_ERROR);
				} else {
					// Validacion duplicidad
					String numeroTelefonico = CriteriaUtil.validateNullLike(request.getParameters().getTelefono().trim(),
							WildcardTypeEnum.BOTH_SIDES);
					UsuarioDO usuarioDO = usuarioRepository.findByMobileNumberAndLadaPais(UsuariosDataConstants.LADA_MEXICO,
							numeroTelefonico);
					if (!ValidatorUtil.isNull(usuarioDO) && !originalUsuarioDO.getUserIdMobileNumber()
							.equals(request.getParameters().getTelefono().trim())) {
						ResponseUtil.addError(request, response,
								UsuarioBusinessErrors.DUPLICATED_USER_MOBILE_NUMBER_ERROR);
						//LOGGER.debug(request, UsuarioBusinessErrors.DUPLICATED_USER_MOBILE_NUMBER_ERROR);
					}
				}
			}
		}

		return ValidatorUtil.isSuccessfulResponse(response);
	}

	@Override
	public ResponseVO<FindDetailUsuarioResponseVO> findDetailUsuario(RequestVO<FindDetailUsuarioRequestVO> request) {
		ResponseVO<FindDetailUsuarioResponseVO> response = new ResponseVO<FindDetailUsuarioResponseVO>();
		if (validaParametrosFindDetailUsuario(request, response)) {
			UsuarioDO usuarioDO = usuarioRepository.findByIdAndActive(request.getParameters().getIdUsuario(), Boolean.TRUE);
			if (ValidatorUtil.isNull(usuarioDO)) {
				ResponseUtil.addError(request, response, UsuarioBusinessErrors.NOT_FOUND_USUARIO_ERROR);
			} else {
				FindDetailUsuarioResponseVO findDetailUsuarioResponseVO = new FindDetailUsuarioResponseVO();
				findDetailUsuarioResponseVO.setId(usuarioDO.getId());
				findDetailUsuarioResponseVO.setImagenPerfil(usuarioDO.getImagenPerfil());
				findDetailUsuarioResponseVO.setLadaPais(usuarioDO.getLadaPais());
				findDetailUsuarioResponseVO.setLastLoginActive(usuarioDO.getLastLoginActive());
				findDetailUsuarioResponseVO.setNotificationsEnabled(Boolean.valueOf(usuarioDO.getNotificationsEnabled().toString()));
				findDetailUsuarioResponseVO.setPasswordStatus(usuarioDO.getPasswordStatus());
				findDetailUsuarioResponseVO.setPasswordStatusChangedAt(usuarioDO.getPasswordStatusChangedAt());
				findDetailUsuarioResponseVO.setTerminosAceptados(usuarioDO.getTerminosAceptados());
				findDetailUsuarioResponseVO.setUserIdEmail(usuarioDO.getUserIdEmail());
				findDetailUsuarioResponseVO.setUserIdMobileDevice(usuarioDO.getUserIdMobileDevice());
				findDetailUsuarioResponseVO.setUserIdMobileNumber(usuarioDO.getUserIdMobileNumber());
				response.setData(findDetailUsuarioResponseVO);
				response.setSuccess(Boolean.TRUE);
			}
		}
		return response;
	}

	private Boolean validaParametrosFindDetailUsuario(RequestVO<FindDetailUsuarioRequestVO> request,
			ResponseVO<FindDetailUsuarioResponseVO> response) {

		if (ValidatorUtil.isNull(request.getParameters())) {
			ResponseUtil.addError(request, response, GeneralBusinessErrors.REQUIRED_PARAMETERS_ERROR);
		} else {
			if (ValidatorUtil.isNullOrZero(request.getParameters().getIdUsuario())) {
				ResponseUtil.addError(request, response, UsuarioBusinessErrors.REQUIRED_ID_USUARIO);
			}
		}

		return ValidatorUtil.isSuccessfulResponse(response);
	}

}
