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
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import de.pecus.api.annotation.Auditable;
import de.pecus.api.constant.GeneralConstants;
import de.pecus.api.entities.UsuarioAplicacionDO;
import de.pecus.api.enums.WildcardTypeEnum;
import de.pecus.api.error.GeneralBusinessErrors;
import de.pecus.api.error.UsuarioBusinessErrors;
import de.pecus.api.exception.PecusException;
import de.pecus.api.repositories.usuarios.UsuarioAplicacionRepository;
import de.pecus.api.services.usuarios.UsuarioAplicacionService;
import de.pecus.api.util.CriteriaUtil;
import de.pecus.api.util.ResponseUtil;
import de.pecus.api.util.ServiceUtil;
import de.pecus.api.util.ValidatorUtil;
import de.pecus.api.vo.RequestVO;
import de.pecus.api.vo.ResponseVO;
import de.pecus.api.vo.usuarios.DeleteUsuarioRequestVO;
import de.pecus.api.vo.usuarios.FindListUsuarioAplicacionResponseVO;
import de.pecus.api.vo.usuarios.FindListUsuarioRequestVO;
import de.pecus.api.vo.usuarios.UpdateUsuarioAplicacionRequestVO;

/**
 * Clase de logica de negocio para administracionde Tipos de Domicilio
 * 
 * @author Luis Enrique Sanchez Santiago
 *
 */
@Service
public class UsuarioAplicacionServiceImpl implements UsuarioAplicacionService {

	@Autowired
	private EntityManager em;

	@Autowired
	private UsuarioAplicacionRepository usuarioAplicacionRepository;


	@Transactional
	@Auditable
	@Override
	public ResponseVO<Boolean> deleteUsuario(RequestVO<DeleteUsuarioRequestVO> request) {
		ResponseVO<Boolean> response = new ResponseVO<Boolean>();

		if (validaParametrosDeleteUsuario(request, response)) {
			// Consultamos el usuario y validamos su existencia
			UsuarioAplicacionDO usuarioAplicacionDO  = exists(request.getParameters().getIdUsuario(), GeneralConstants.IDIOMA_ES_MX);
			// Deshabilitamos al usuario por medio de la bandera de active
			ServiceUtil.setDisabledEntity(usuarioAplicacionDO, request.getToken());
			// Actualizamos el usuario
			usuarioAplicacionRepository.saveAndFlush(usuarioAplicacionDO);
			response.setData(Boolean.TRUE);
			response.setSuccess(Boolean.TRUE);
		}

		return response;
	}
	

	@Transactional
	@Auditable
	@Override
	public ResponseVO<Long> updateUsuario(RequestVO<UpdateUsuarioAplicacionRequestVO> request) {
		ResponseVO<Long> response = new ResponseVO<Long>();

		if (validaParametrosUpdateUsuario(request, response)) {
			// Consultamos el usuario y validamos su existencia
			UsuarioAplicacionDO usuarioAplicacionDO = exists(request.getParameters().getId(), GeneralConstants.IDIOMA_ES_MX);
			
			if (!ValidatorUtil.isNull(request.getParameters().getPassword())) {
				usuarioAplicacionDO.setPassword(request.getParameters().getPassword());
			}
			
			if (!ValidatorUtil.isNull(request.getParameters().getClientID())) {
				usuarioAplicacionDO.setClientID(request.getParameters().getClientID());
			}
			
			if (!ValidatorUtil.isNull(request.getParameters().getSecret())) {
				usuarioAplicacionDO.setSecret(request.getParameters().getSecret());
			}
			
			ServiceUtil.setAuditFields(usuarioAplicacionDO, request.getToken());
			// Actualizamos el usuario
			usuarioAplicacionRepository.saveAndFlush(usuarioAplicacionDO);
			response.setData(usuarioAplicacionDO.getId());
			response.setSuccess(Boolean.TRUE);
		}

		return response;
	}


	private Boolean validaParametrosUpdateUsuario(RequestVO<UpdateUsuarioAplicacionRequestVO> request,
			ResponseVO<Long> response) {

		if (ValidatorUtil.isNull(request.getParameters())) {
			ResponseUtil.addError(request, response, GeneralBusinessErrors.REQUIRED_PARAMETERS_ERROR);
		} else {
			
			if (ValidatorUtil.isNullOrZero(request.getParameters().getId())) {
				ResponseUtil.addError(request, response, UsuarioBusinessErrors.REQUIRED_ID_USUARIO);
			} 
			
		}

		return ValidatorUtil.isSuccessfulResponse(response);
	}
	
	/**
	 * Metodo encargado de validar la existencia del usuario por su identificador
	 * 
	 * @param idUsuario
	 */
	public UsuarioAplicacionDO exists(Long idUsuario, String idioma) {

		// Validacion de datos de entrada
		if (ValidatorUtil.isNullOrZero(idUsuario)) {
			throw new PecusException(UsuarioBusinessErrors.REQUIRED_USUARIO_ERROR, idioma);
		}

		// Consulta
		UsuarioAplicacionDO usuario = usuarioAplicacionRepository.findByIdAndActive(idUsuario, Boolean.TRUE);

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

	
	@Override
	public ResponseVO<List<FindListUsuarioAplicacionResponseVO>> findListUsuarioApplicacion(
			RequestVO<FindListUsuarioRequestVO> request) {
		ResponseVO<List<FindListUsuarioAplicacionResponseVO>> response  = new ResponseVO<List<FindListUsuarioAplicacionResponseVO>>();
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
	
	private Boolean validaParametrosFindListUsuario(RequestVO<FindListUsuarioRequestVO> request, ResponseVO<List<FindListUsuarioAplicacionResponseVO>> response) {
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
    private List<FindListUsuarioAplicacionResponseVO> transformFindListUsuarioApp(List<UsuarioAplicacionDO> usuarioDOs){
    	List<FindListUsuarioAplicacionResponseVO> findListUsuarioResponseVOs = new ArrayList<FindListUsuarioAplicacionResponseVO>();
    	for(UsuarioAplicacionDO usuarioDO : usuarioDOs) {
    		FindListUsuarioAplicacionResponseVO findListUsuarioResponseVO = new FindListUsuarioAplicacionResponseVO();
    		findListUsuarioResponseVO.setId(usuarioDO.getId());
    		findListUsuarioResponseVO.setUserIdEmail(usuarioDO.getUserIdEmail());
    		findListUsuarioResponseVO.setToken(usuarioDO.getRefreshTokenToken());
    		findListUsuarioResponseVO.setClientID(usuarioDO.getClientID());
    		findListUsuarioResponseVO.setSecret(usuarioDO.getSecret());
    		findListUsuarioResponseVOs.add(findListUsuarioResponseVO);
    	}
    	
    	return findListUsuarioResponseVOs;
    }




	
}