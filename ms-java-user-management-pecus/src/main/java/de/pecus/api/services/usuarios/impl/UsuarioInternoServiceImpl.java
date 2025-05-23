package de.pecus.api.services.usuarios.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import de.pecus.api.annotation.Auditable;
import de.pecus.api.constant.GeneralConstants;
import de.pecus.api.constant.UsuariosDataConstants;
import de.pecus.api.entities.RolDO;
import de.pecus.api.entities.UsuarioAplicacionDO;
import de.pecus.api.entities.UsuarioInternoDO;
import de.pecus.api.enums.WildcardTypeEnum;
import de.pecus.api.error.GeneralBusinessErrors;
import de.pecus.api.error.UsuarioBusinessErrors;
import de.pecus.api.exception.PecusException;
import de.pecus.api.log.SmartLogger;
import de.pecus.api.log.SmartLoggerFactory;
import de.pecus.api.repositories.usuarios.UsuarioInternalRepository;
import de.pecus.api.services.usuarios.RolService;
import de.pecus.api.services.usuarios.UsuarioInternoRolService;
import de.pecus.api.services.usuarios.UsuarioInternoService;
import de.pecus.api.util.CriteriaUtil;
import de.pecus.api.util.RequestVOUtil;
import de.pecus.api.util.ResponseUtil;
import de.pecus.api.util.ServiceUtil;
import de.pecus.api.util.StringUtil;
import de.pecus.api.util.ValidatorUtil;
import de.pecus.api.vo.RequestVO;
import de.pecus.api.vo.ResponseVO;
import de.pecus.api.vo.usuarios.AddRolUserRequestVO;
import de.pecus.api.vo.usuarios.CrearUsuarioRequestVO;
import de.pecus.api.vo.usuarios.CreateUsuarioRolRequestVO;
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
public class UsuarioInternoServiceImpl implements UsuarioInternoService {

	/** Logger */
	private static final SmartLogger LOGGER = SmartLoggerFactory.getLogger(UsuarioInternoServiceImpl.class);
	
	@Autowired
	private EntityManager em;

	@Autowired
	private UsuarioInternalRepository usuarioInternalRepository;
	
	@Autowired
	private RolService rolService;
	
	@Autowired
	private UsuarioInternoRolService usuarioInternoRolService;

	@Override
	public ResponseVO<Long> crearUsuarioInterno(RequestVO<CrearUsuarioRequestVO> request) {
		ResponseVO<Long> response = new ResponseVO<Long>();

		if (validaParametrosCrearUsuario(request, response)) {
			// Seteamos la información del usuario
			UsuarioInternoDO usuarioInternoDO = new UsuarioInternoDO();
			usuarioInternoDO.setImagenPerfil(request.getParameters().getImagenPerfil());
			usuarioInternoDO.setUserIdEmail(request.getParameters().getEmail());
			usuarioInternoDO.setNotificationEnabled(request.getParameters().getNotificaciones());
			ServiceUtil.setAuditFields(usuarioInternoDO, request.getToken());

			usuarioInternoDO = usuarioInternalRepository.saveAndFlush(usuarioInternoDO);
			response.setData(usuarioInternoDO.getId());
			response.setSuccess(Boolean.TRUE);
		}

		return response;
	}

	@Transactional
	@Auditable
	@Override
	public ResponseVO<Long> updateUsuario(RequestVO<UpdateUsuarioRequestVO> request) {

		ResponseVO<Long> response = new ResponseVO<Long>();

		if (validaParametrosUpdateUsuario(request, response)) {
			// Consultamos el usuario y validamos su existencia
			UsuarioInternoDO usuarioInternoDO = exists(request.getParameters().getId(), GeneralConstants.IDIOMA_ES_MX);
			if (!ValidatorUtil.isNullOrEmpty(request.getParameters().getImagenPerfil())) {
				usuarioInternoDO.setImagenPerfil(request.getParameters().getImagenPerfil());
			}
			if (!ValidatorUtil.isNullOrEmpty(request.getParameters().getEmail())) {
				usuarioInternoDO.setUserIdEmail(request.getParameters().getEmail());
			}
			if (!ValidatorUtil.isNull(request.getParameters().getNotificaciones())) {
				usuarioInternoDO.setNotificationEnabled(request.getParameters().getNotificaciones());
			}
			ServiceUtil.setAuditFields(usuarioInternoDO, request.getToken());
			// Actualizamos el usuario
			usuarioInternalRepository.saveAndFlush(usuarioInternoDO);
			response.setData(usuarioInternoDO.getId());
			response.setSuccess(Boolean.TRUE);
		}

		return response;
	}

	@Auditable
	@Override
	public ResponseVO<FindDetailUsuarioResponseVO> findDetailUsuario(RequestVO<FindDetailUsuarioRequestVO> request) {
		ResponseVO<FindDetailUsuarioResponseVO> response = new ResponseVO<FindDetailUsuarioResponseVO>();
		if (validaParametrosFindDetailUsuario(request, response)) {
			UsuarioInternoDO usuarioInternoDO = usuarioInternalRepository
					.findByIdAndActive(request.getParameters().getIdUsuario(), Boolean.TRUE);
			if (ValidatorUtil.isNull(usuarioInternoDO)) {
				ResponseUtil.addError(request, response, UsuarioBusinessErrors.NOT_FOUND_USUARIO_ERROR);
			} else {
				FindDetailUsuarioResponseVO findDetailUsuarioResponseVO = new FindDetailUsuarioResponseVO();
				findDetailUsuarioResponseVO.setId(usuarioInternoDO.getId());
				findDetailUsuarioResponseVO.setImagenPerfil(usuarioInternoDO.getImagenPerfil());
				findDetailUsuarioResponseVO.setNotificationsEnabled(usuarioInternoDO.getNotificationEnabled());
				findDetailUsuarioResponseVO.setUserIdEmail(usuarioInternoDO.getUserIdEmail());
				response.setData(findDetailUsuarioResponseVO);
				response.setSuccess(Boolean.TRUE);
			}
		}
		return response;
	}

	@Transactional
	@Auditable
	@Override
	public ResponseVO<Boolean> deleteUsuario(RequestVO<DeleteUsuarioRequestVO> request) {
		ResponseVO<Boolean> response = new ResponseVO<Boolean>();

		if (validaParametrosDeleteUsuario(request, response)) {
			// Consultamos el usuario y validamos su existencia
			UsuarioInternoDO usuarioInternoDO = exists(request.getParameters().getIdUsuario(), GeneralConstants.IDIOMA_ES_MX);
			// Deshabilitamos al usuario por medio de la bandera de active
			ServiceUtil.setDisabledEntity(usuarioInternoDO, request.getToken());
			// Actualizamos el usuario
			usuarioInternalRepository.saveAndFlush(usuarioInternoDO);
			response.setData(Boolean.TRUE);
			response.setSuccess(Boolean.TRUE);
		}

		return response;
	}
	
	/**
	 * Metodo que agrega un rol a un usuario existente
	 * 
	 * @param request
	 * @return
	 */
	@Auditable
	@Transactional
	public ResponseVO<Long> addRolUser(RequestVO<AddRolUserRequestVO> request) {
		ResponseVO<Long> response = new ResponseVO<>();
		// Validamos los parametros de entrada
		List<CreateUsuarioRolRequestVO> createUsuarioRolRequestVOs = validateParamsAddRolUser(request, response);
		// Validamos si durante las validaciones de parametros de entrada no hubo
		// errores
		if (ValidatorUtil.isSuccessfulResponse(response)) {
			// Generamos el requestVO para invocar el servicio de UsuarioRolService
			RequestVO<List<CreateUsuarioRolRequestVO>> usuarioRolServiceRequest = RequestVOUtil.setNewRequestVO(request,
					createUsuarioRolRequestVOs);
			// Invocamos al servicio para crear la relacion del usuario con el rol
			// especificado
			ResponseVO<Long> usuarioRolServiceResponse = usuarioInternoRolService.create(usuarioRolServiceRequest);
			// Validamos la respuesta del servicio UsuarioRolService
			if (ValidatorUtil.isSuccessfulResponse(usuarioRolServiceResponse)) {
				// En caso de que el rol asociado sea medico, le asignamos el id del doctor que
				// viene en el request
				if (UsuariosDataConstants.NOMBRE_ROL_MEDICO.equals(request.getParameters().getRolName())) {
					// Obtenemos el usuario
					UsuarioInternoDO usuarioInternoDO = usuarioInternalRepository.getById(request.getParameters().getUserId());
					// Seteamos los campos de auditoria
					ServiceUtil.setAuditFields(usuarioInternoDO, request.getToken());
					// Actualizamos el usuario
					usuarioInternalRepository.saveAndFlush(usuarioInternoDO);
				}
				response.setData(usuarioRolServiceResponse.getData());
				response.setSuccess(Boolean.TRUE);
			} else {
				response.setErrors(usuarioRolServiceResponse.getErrors());
			}

		}
		return response;
	}

	private Boolean validaParametrosCrearUsuario(RequestVO<CrearUsuarioRequestVO> request, ResponseVO<Long> response) {

		if (ValidatorUtil.isNull(request.getParameters())) {
			ResponseUtil.addError(request, response, GeneralBusinessErrors.REQUIRED_PARAMETERS_ERROR);
		} else {
			// Valida el correo electronico registrado.
			if (!ValidatorUtil.isNullOrEmpty(request.getParameters().getEmail())) {

				// Validacion de formato de correo electronico valido
				if (!StringUtil.isValidEmail(request.getParameters().getEmail())) {
					ResponseUtil.addError(request, response, UsuarioBusinessErrors.INVALID_EMAIL_FORMAT_ERROR);
					LOGGER.debug(request, UsuarioBusinessErrors.INVALID_EMAIL_FORMAT_ERROR);
				}

				// Validacion duplicidad de correo electronico
				UsuarioInternoDO usuarioDO = usuarioInternalRepository
						.findByUserIdEmail(request.getParameters().getEmail());
				if (!ValidatorUtil.isNull(usuarioDO)) {
					ResponseUtil.addError(request, response, UsuarioBusinessErrors.DUPLICATED_USER_ID_EMAIL_ERROR);
					LOGGER.debug(request, UsuarioBusinessErrors.DUPLICATED_USER_ID_EMAIL_ERROR);
				}
			}

		}

		return ValidatorUtil.isSuccessfulResponse(response);
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

	private Boolean validaParametrosUpdateUsuario(RequestVO<UpdateUsuarioRequestVO> request,
			ResponseVO<Long> response) {

		if (ValidatorUtil.isNull(request.getParameters())) {
			ResponseUtil.addError(request, response, GeneralBusinessErrors.REQUIRED_PARAMETERS_ERROR);
		} else {
			if (ValidatorUtil.isNullOrZero(request.getParameters().getId())) {
				ResponseUtil.addError(request, response, UsuarioBusinessErrors.REQUIRED_ID_USUARIO);
			} else {

				// Buscamos el usuario original
				UsuarioInternoDO originalUsuarioDO = usuarioInternalRepository
						.findByIdAndActive(request.getParameters().getId(), Boolean.TRUE);
				// Valida el correo electronico registrado.
				if (!ValidatorUtil.isNullOrEmpty(request.getParameters().getEmail())) {

					// Validacion de formato de correo electronico valido
					if (!StringUtil.isValidEmail(request.getParameters().getEmail())) {
						ResponseUtil.addError(request, response, UsuarioBusinessErrors.INVALID_EMAIL_FORMAT_ERROR);
						LOGGER.debug(request, UsuarioBusinessErrors.INVALID_EMAIL_FORMAT_ERROR);
					}

					// Validacion duplicidad de correo electronico
					UsuarioInternoDO usuarioInternoDO = usuarioInternalRepository
							.findByUserIdEmail(request.getParameters().getEmail());
					if (!ValidatorUtil.isNull(usuarioInternoDO)
							&& !originalUsuarioDO.getUserIdEmail().equals(request.getParameters().getEmail())) {
						ResponseUtil.addError(request, response, UsuarioBusinessErrors.DUPLICATED_USER_ID_EMAIL_ERROR);
						LOGGER.debug(request, UsuarioBusinessErrors.DUPLICATED_USER_ID_EMAIL_ERROR);
					}
				}
			}
		}

		return ValidatorUtil.isSuccessfulResponse(response);
	}
	
	/**
	 * Metodo encargado de validar la existencia del usuario por su identificador
	 * 
	 * @param idUsuario
	 */
	public UsuarioInternoDO exists(Long idUsuario, String idioma) {

		// Validacion de datos de entrada
		if (ValidatorUtil.isNullOrZero(idUsuario)) {
			throw new PecusException(UsuarioBusinessErrors.REQUIRED_USUARIO_ERROR, idioma);
		}

		// Consulta
		UsuarioInternoDO usuario = usuarioInternalRepository.findByIdAndActive(idUsuario, Boolean.TRUE);

		// Validacion de existencia
		if (ValidatorUtil.isNull(usuario)) {
			// Genera error
			throw new PecusException(UsuarioBusinessErrors.NOT_FOUND_USUARIO_ERROR, idioma);
		}
		return usuario;
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

	/**
	 * Validamos los parametros de entrada del servicio addRolUser
	 * 
	 * @param request
	 * @param response
	 * @return CreateUsuarioRolRequestVO
	 */
	private List<CreateUsuarioRolRequestVO> validateParamsAddRolUser(RequestVO<AddRolUserRequestVO> request,
			ResponseVO<Long> response) {

		List<CreateUsuarioRolRequestVO> listUsuarioRolRequestVOs = null;

		// Validamos si se especificaron los parametros de entrada
		if (ValidatorUtil.isNull(request.getParameters())) {
			// Si no se ha informado ningun parametro regresa el error y no sigue validando
			LOGGER.debug(request, GeneralBusinessErrors.REQUIRED_PARAMETERS_ERROR);
			ResponseUtil.addError(request, response, GeneralBusinessErrors.REQUIRED_PARAMETERS_ERROR);
		} else {
			String idioma = ValidatorUtil.isNullOrEmpty(request.getIdioma()) ? GeneralConstants.IDIOMA_ES_MX
					: request.getIdioma();
			// Validamos si se especifico el id de usuario
			if (ValidatorUtil.isNullOrZero(request.getParameters().getUserId())) {
				ResponseUtil.addError(request, response, UsuarioBusinessErrors.REQUIRED_ID_ERROR);
			} else {
				UsuarioInternoDO usuarioInternoDO = exists(request.getParameters().getUserId(), idioma);
				if (ValidatorUtil.isNull(usuarioInternoDO)) {
					ResponseUtil.addError(request, response, UsuarioBusinessErrors.NOT_FOUND_USUARIO_ERROR);
				} else {
					if (ValidatorUtil.isNullOrEmpty(request.getParameters().getRolName())) {
						ResponseUtil.addError(request, response, UsuarioBusinessErrors.REQUIRED_ROL_ERROR);
					} else {
						List<RolDO> rolListDO = rolService.exists(request.getParameters().getRolName(), idioma);
						if (ValidatorUtil.isNullOrEmpty(rolListDO)) {
							ResponseUtil.addError(request, response, UsuarioBusinessErrors.NOT_FOUND_ROL_ERROR);
						} else {
							listUsuarioRolRequestVOs = new ArrayList<CreateUsuarioRolRequestVO>();
							CreateUsuarioRolRequestVO createUsuarioRolRequestVO = null;
							for(RolDO rolDO : rolListDO) {
								if (UsuariosDataConstants.NOMBRE_ROL_MEDICO.equals(request.getParameters().getRolName())
										&& ValidatorUtil.isNullOrZero(request.getParameters().getDoctorId())) {
									ResponseUtil.addError(request, response, UsuarioBusinessErrors.REQUIRED_DOCTOR_ID);
								} else {
									createUsuarioRolRequestVO = new CreateUsuarioRolRequestVO();
									createUsuarioRolRequestVO.setFechaInicio(Calendar.getInstance().getTime());
									createUsuarioRolRequestVO.setIdRol(rolDO.getId());
									createUsuarioRolRequestVO.setIdUser(usuarioInternoDO.getId());
									listUsuarioRolRequestVOs.add(createUsuarioRolRequestVO);
								}
							}
						}
					}
				}
			}
		}
		return listUsuarioRolRequestVOs;
	}
	
	@Override
	public ResponseVO<List<FindListUsuarioResponseVO>> findListUsuario(RequestVO<FindListUsuarioRequestVO> request) {
		ResponseVO<List<FindListUsuarioResponseVO>> response  = new ResponseVO<List<FindListUsuarioResponseVO>>();
		List<UsuarioInternoDO> usuarioDOs = null;
		
		if(validaParametrosFindListUsuario(request, response)) {
			String orderbyCriteria = request.getOrderBy();
			String ordertypeCriteria = request.getOrderType();
			
			String orderBy = ValidatorUtil.isNullOrEmpty(orderbyCriteria) ? "id" : orderbyCriteria;
			Direction orderType = ValidatorUtil.isNullOrEmpty(ordertypeCriteria) || ordertypeCriteria.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
			
			Integer size = ValidatorUtil.isNullOrZero(request.getSize()) ? 10 : request.getSize();
			Integer page = ValidatorUtil.isNullOrZero(request.getPage()) ? 1 : request.getPage();
			
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<UsuarioInternoDO> cq = cb.createQuery(UsuarioInternoDO.class);
			CriteriaQuery<Long> cqCount = cb.createQuery(Long.class);
			
			Root<UsuarioInternoDO> usuarioRoot = cq.from(UsuarioInternoDO.class);
			Root<UsuarioInternoDO> usuarioRootCount = cqCount.from(UsuarioInternoDO.class);
			
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
			
			TypedQuery<UsuarioInternoDO> query = em.createQuery(cq);
			
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
	
	
	@Override
	public ResponseVO<List<FindListUsuarioResponseVO>> findListUsuarioApplicacion(
			RequestVO<FindListUsuarioRequestVO> request) {
		ResponseVO<List<FindListUsuarioResponseVO>> response  = new ResponseVO<List<FindListUsuarioResponseVO>>();
		List<UsuarioAplicacionDO> usuarioDOs = null;
		
		if(validaParametrosFindListUsuario(request, response)) {
			String orderbyCriteria = request.getOrderBy();
			String ordertypeCriteria = request.getOrderType();
			
			String orderBy = ValidatorUtil.isNullOrEmpty(orderbyCriteria) ? "id" : orderbyCriteria;
			Direction orderType = ValidatorUtil.isNullOrEmpty(ordertypeCriteria) || ordertypeCriteria.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
			
			Integer size = ValidatorUtil.isNullOrZero(request.getSize()) ? 10 : request.getSize();
			Integer page = ValidatorUtil.isNullOrZero(request.getPage()) ? 1 : request.getPage();
			
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<UsuarioAplicacionDO> cq = cb.createQuery(UsuarioAplicacionDO.class);
			CriteriaQuery<Long> cqCount = cb.createQuery(Long.class);
			
			Root<UsuarioAplicacionDO> usuarioRoot = cq.from(UsuarioAplicacionDO.class);
			Root<UsuarioAplicacionDO> usuarioRootCount = cqCount.from(UsuarioAplicacionDO.class);
			
			List<Predicate> predicates = buildPredicatesApp(request, cb, usuarioRoot);
			List<Predicate> predicatesCount = buildPredicatesApp(request, cb, usuarioRootCount);
			
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
			
			TypedQuery<UsuarioAplicacionDO> query = em.createQuery(cq);
			
			// Agregamos la paginacion
			query.setFirstResult(((page - 1) * size));
			query.setMaxResults(size);
			
			usuarioDOs = query.getResultList();
			
			if (ValidatorUtil.isNullOrEmpty(usuarioDOs)) {
				ResponseUtil.addError(request, response, UsuarioBusinessErrors.NOT_FOUND_USUARIO_ERROR);
			} else {
				response.setTotalRows(count);
				response.setSuccess(Boolean.TRUE);
				response.setData(transformFindListUsuarioApp(usuarioDOs));
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
	
	
	/**
     * Armado de query usando criteria builder
     * @param requestVO
     * @param cb
     * @param usuarioRoot
     * @return
     */
    private List<Predicate> buildPredicates(RequestVO<FindListUsuarioRequestVO> requestVO, CriteriaBuilder cb,
			Root<UsuarioInternoDO> usuarioRoot) {
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (!ValidatorUtil.isNullOrEmpty(requestVO.getParameters().getEmail())) {
			Predicate usuarioIdEmailPredicate = cb.like(usuarioRoot.get("userIdEmail"),
					CriteriaUtil.validateNullLike(requestVO.getParameters().getEmail(), WildcardTypeEnum.BOTH_SIDES));
			predicates.add(usuarioIdEmailPredicate);
		}
		
		Predicate activePredicate = cb.equal(usuarioRoot.get("active"), Boolean.TRUE);
		predicates.add(activePredicate);
		
		return predicates;
	}
    
    /**
     * Transformacion de usaurio interno mediante criteria
     * @param usuarioDOs
     * @return
     */
    private List<FindListUsuarioResponseVO> transformFindListUsuarioDO(List<UsuarioInternoDO> usuarioDOs){
    	List<FindListUsuarioResponseVO> findListUsuarioResponseVOs = new ArrayList<FindListUsuarioResponseVO>();
    	for(UsuarioInternoDO usuarioDO : usuarioDOs) {
    		FindListUsuarioResponseVO findListUsuarioResponseVO = new FindListUsuarioResponseVO();
    		findListUsuarioResponseVO.setId(usuarioDO.getId());
    		findListUsuarioResponseVO.setImagenPerfil(usuarioDO.getImagenPerfil());
    		findListUsuarioResponseVO.setUserIdEmail(usuarioDO.getUserIdEmail());
    		findListUsuarioResponseVOs.add(findListUsuarioResponseVO);
    	}
    	
    	return findListUsuarioResponseVOs;
    }
    
    
    /**
     * Armado de query usando criteria builder
     * @param requestVO
     * @param cb
     * @param usuarioRoot
     * @return
     */
    private List<Predicate> buildPredicatesApp(RequestVO<FindListUsuarioRequestVO> requestVO, CriteriaBuilder cb,
			Root<UsuarioAplicacionDO> usuarioRoot) {
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (!ValidatorUtil.isNullOrEmpty(requestVO.getParameters().getEmail())) {
			Predicate usuarioIdEmailPredicate = cb.like(usuarioRoot.get("userIdEmail"),
					CriteriaUtil.validateNullLike(requestVO.getParameters().getEmail(), WildcardTypeEnum.BOTH_SIDES));
			predicates.add(usuarioIdEmailPredicate);
		}
		
		Predicate activePredicate = cb.equal(usuarioRoot.get("active"), Boolean.TRUE);
		predicates.add(activePredicate);
		
		return predicates;
	}
    
    /**
     * Transformacion de usaurio aplicaccion mediante criteria
     * @param usuarioDOs
     * @return
     */
    private List<FindListUsuarioResponseVO> transformFindListUsuarioApp(List<UsuarioAplicacionDO> usuarioDOs){
    	List<FindListUsuarioResponseVO> findListUsuarioResponseVOs = new ArrayList<FindListUsuarioResponseVO>();
    	for(UsuarioAplicacionDO usuarioDO : usuarioDOs) {
    		FindListUsuarioResponseVO findListUsuarioResponseVO = new FindListUsuarioResponseVO();
    		findListUsuarioResponseVO.setId(usuarioDO.getId());
    		findListUsuarioResponseVO.setUserIdEmail(usuarioDO.getUserIdEmail());
    		findListUsuarioResponseVOs.add(findListUsuarioResponseVO);
    	}
    	
    	return findListUsuarioResponseVOs;
    }
}