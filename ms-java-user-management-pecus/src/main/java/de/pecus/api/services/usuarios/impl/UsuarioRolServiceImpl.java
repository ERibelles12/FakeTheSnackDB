package de.pecus.api.services.usuarios.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.pecus.api.annotation.Auditable;
import de.pecus.api.constant.GeneralConstants;
import de.pecus.api.entities.RelUsuarioRolDO;
import de.pecus.api.entities.RolDO;
import de.pecus.api.entities.UsuarioDO;
import de.pecus.api.error.GeneralBusinessErrors;
import de.pecus.api.error.UsuarioBusinessErrors;
import de.pecus.api.exception.PecusException;
import de.pecus.api.log.SmartLogger;
import de.pecus.api.log.SmartLoggerFactory;
import de.pecus.api.repositories.usuarios.UsuarioRolRepository;
import de.pecus.api.services.usuarios.RolService;
import de.pecus.api.services.usuarios.UsuarioRolService;
import de.pecus.api.services.usuarios.UsuarioService;
import de.pecus.api.util.ResponseUtil;
import de.pecus.api.util.ServiceUtil;
import de.pecus.api.util.ValidatorUtil;
import de.pecus.api.vo.RequestVO;
import de.pecus.api.vo.ResponseVO;
import de.pecus.api.vo.usuarios.CreateUsuarioRolRequestVO;
import de.pecus.api.vo.usuarios.DeleteUsuarioRolRequestVO;

/**
 * Servicio para la administracion de la relacion USUARIO_ROL
 * @author Luis Enrique Sanchez Santiago
 *
 */
@Service
public class UsuarioRolServiceImpl implements UsuarioRolService{
	
	/**
	 * Smartlogger
	 */
	private static final SmartLogger LOGGER = SmartLoggerFactory.getLogger(UsuarioRolServiceImpl.class);

	/**
	 * Referencia al repository
	 */
	@Autowired
	private UsuarioRolRepository usuarioRolRepository;
	@Autowired
	private UsuarioService userMobileService;
	@Autowired
	private RolService rolService;
	
	/**
	 * Valida que los parametros para la operacion de insercion sean correctos
	 * 
	 * @param request  Objeto con los parametros de entrada
	 * @param response Respuesta donde se agregan los errores
	 * @return true si todos los parametros son correctos
	 */
	private boolean validateParametersCreate(RequestVO<List<CreateUsuarioRolRequestVO>> request,
			ResponseVO<Long> response) {
		
		// Valida parametros de entrada
		if (ValidatorUtil.isNullOrEmpty(request.getParameters())) {
			ResponseUtil.addError(request, response, GeneralBusinessErrors.REQUIRED_PARAMETERS_ERROR);
			return false;
		}else {
			for(CreateUsuarioRolRequestVO createUsuarioRolRequestVO : request.getParameters()) {
				// valida fecha inicio requerido
				if (ValidatorUtil.isNull(createUsuarioRolRequestVO.getFechaInicio())) {
					ResponseUtil.addError(request, response, UsuarioBusinessErrors.REQUIRED_FECHA_INICIO_ERROR);
				}
			}
		}
		return ValidatorUtil.isSuccessfulResponse(response);
	}
	/**
	 * Crea un nuevo registro de Usuario Condo Rol
	 * 
	 * @param request 
	 * @return Id generado
	 */
	@Auditable
	@Transactional
	public ResponseVO<Long> create(RequestVO<List<CreateUsuarioRolRequestVO>> request){
		ResponseVO<Long> response = new ResponseVO<>();
		String idioma = request.getIdioma();
		
		if (validateParametersCreate(request, response)) {
			List<RelUsuarioRolDO> relUsuarioRolDOs = new ArrayList<RelUsuarioRolDO>();
			for(CreateUsuarioRolRequestVO createUsuarioRolRequestVO : request.getParameters()) {
				UsuarioDO usuarioDO = userMobileService.exists(createUsuarioRolRequestVO.getIdUser(), idioma);
				// se valida que el rol exista en la aplciacion
				RolDO rol = rolService.exists(createUsuarioRolRequestVO.getIdRol(), idioma);
				
				validateExistUserRol(request, response, idioma, usuarioDO, rol);
				
				RelUsuarioRolDO relUsuarioRolDO = new RelUsuarioRolDO();
				relUsuarioRolDO.setFechaInicio(createUsuarioRolRequestVO.getFechaInicio());
				relUsuarioRolDO.setRol(rol);
				relUsuarioRolDO.setUsuario(usuarioDO);
				
				ServiceUtil.setAuditFields(relUsuarioRolDO, request.getToken());
				
				relUsuarioRolDOs.add(relUsuarioRolDO);
			}
			if(ValidatorUtil.isSuccessfulResponse(response)) {
				List<RelUsuarioRolDO> registrosCreados = usuarioRolRepository.saveAll(relUsuarioRolDOs);
	
				if(!ValidatorUtil.isNullOrEmpty(registrosCreados)) {
					response.setSuccess(Boolean.TRUE);
					response.setData(Long.valueOf(registrosCreados.size()));
				}
			}
			
		}
		return response;
	}
	
	/**
	 * Metodo que se encarga de validar si ya existe la relacion entre un usuario y un rol
	 * @param request
	 * @param response
	 * @param idioma
	 * @param usuarioDO
	 * @param rol
	 */
	private void validateExistUserRol(RequestVO<?> request, ResponseVO<Long> response,
			String idioma, UsuarioDO usuarioDO, RolDO rol) {
		try {
			List<RelUsuarioRolDO> usuarioRolDOs = exist(usuarioDO.getId(), rol.getId(), idioma);
			if(!ValidatorUtil.isNullOrEmpty(usuarioRolDOs)) {
				ResponseUtil.addError(request, response, UsuarioBusinessErrors.EXIST_USER_ROL_ERROR);
			}
		} catch (PecusException e) {
			// Solo logueamos el error ya que solo queremos validar que no haya resultados
			LOGGER.error(request, response, idioma, e);
		}
	}
	
	/**
	 * Metodo que valida si existe la relacion entre usuario y rol
	 */
	@Override
	public List<RelUsuarioRolDO> exist(Long userId, Long rolId, String idioma) {
		// Validacion de datos de entrada
		if (ValidatorUtil.isNullOrZero(userId)) {
			throw new PecusException(UsuarioBusinessErrors.REQUIRED_ID_ERROR, idioma);
		}
		
		if (ValidatorUtil.isNullOrZero(rolId)) {
			throw new PecusException(UsuarioBusinessErrors.REQUIRED_ID_ROL_ERROR, idioma);
		}
		List<RelUsuarioRolDO> usuarioRolDOs =  usuarioRolRepository.findByUserIdAndRolIdAndActive(userId, rolId, Boolean.TRUE);
		
		if(ValidatorUtil.isNullOrEmpty(usuarioRolDOs)) {
			// Genera error
			throw new PecusException(UsuarioBusinessErrors.NOT_FOUND_USUARIO_ROL_ERROR, idioma);
		}
		
		return usuarioRolDOs;
	}
	
	/**
	 * Metodo que se encarga de eliminar una relacion Usuario Rol
	 */
	@Transactional
	@Auditable
	@Override
	public ResponseVO<Boolean> deleteUsuarioRol(RequestVO<DeleteUsuarioRolRequestVO> request) {
		ResponseVO<Boolean> response = new ResponseVO<Boolean>();

		if (validaParametrosDeleteUsuarioRol(request, response)) {
			// Consultamos el usuario y validamos su existencia
			RelUsuarioRolDO usuarioRolDO = existUsuarioRol(request.getParameters().getIdUsuario(), request.getParameters().getIdRol(), GeneralConstants.IDIOMA_ES_MX);
			// Deshabilitamos al usuario por medio de la bandera de active
			//ServiceUtil.setDisabledEntity(usuarioRolDO, request.getToken());
			usuarioRolRepository.deleteById(usuarioRolDO.getId());
			// Actualizamos el usuario
			//usuarioRolRepository.saveAndFlush(usuarioRolDO);
			response.setData(Boolean.TRUE);
			response.setSuccess(Boolean.TRUE);
		}

		return response;
	}
	
	/**
	 * Valida parametros para la operacion de borrado de rol de usuario
	 * @param request
	 * @param response
	 * @return
	 */
	private boolean validaParametrosDeleteUsuarioRol(RequestVO<DeleteUsuarioRolRequestVO> request, ResponseVO<Boolean> response) {
		
		if (ValidatorUtil.isNull(request.getParameters())) {
			ResponseUtil.addError(request, response, GeneralBusinessErrors.REQUIRED_PARAMETERS_ERROR);
		} else {
			if (ValidatorUtil.isNullOrZero(request.getParameters().getIdUsuario())) {
				ResponseUtil.addError(request, response, UsuarioBusinessErrors.REQUIRED_ID_USUARIO);
			}
			if (ValidatorUtil.isNullOrZero(request.getParameters().getIdRol())) {
				ResponseUtil.addError(request, response, UsuarioBusinessErrors.REQUIRED_ID_ROL_ERROR);
			}
		}

		return ValidatorUtil.isSuccessfulResponse(response);
		
	}
	
	/**
	 * Metodo que valida si existe la relacion entre usuario y rol
	 */
	private RelUsuarioRolDO existUsuarioRol(Long userId, Long rolId, String idioma) {
		// Validacion de datos de entrada
		if (ValidatorUtil.isNullOrZero(userId)) {
			throw new PecusException(UsuarioBusinessErrors.REQUIRED_ID_ERROR, idioma);
		}
		
		if (ValidatorUtil.isNullOrZero(rolId)) {
			throw new PecusException(UsuarioBusinessErrors.REQUIRED_ID_ROL_ERROR, idioma);
		}
		RelUsuarioRolDO usuarioRolDOs =  usuarioRolRepository.findUsuarioRolActivo(userId, rolId);
		
		if(ValidatorUtil.isNull(usuarioRolDOs)) {
			// Genera error
			throw new PecusException(UsuarioBusinessErrors.NOT_FOUND_USUARIO_ROL_ERROR, idioma);
		}
		
		return usuarioRolDOs;
	}
}
