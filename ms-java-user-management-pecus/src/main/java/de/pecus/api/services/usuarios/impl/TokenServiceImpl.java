package de.pecus.api.services.usuarios.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import de.pecus.api.constant.GeneralConstants;
import de.pecus.api.constant.UsuariosDataConstants;
import de.pecus.api.entities.CodigoActivacionDO;
import de.pecus.api.entities.UsuarioDO;
import de.pecus.api.enums.arqcomponentes.AplicacionEnum;
import de.pecus.api.error.GeneralBusinessErrors;
import de.pecus.api.error.UsuarioBusinessErrors;
import de.pecus.api.log.SmartLogger;
import de.pecus.api.log.SmartLoggerFactory;
import de.pecus.api.repositories.usuarios.CodigoRepository;
import de.pecus.api.repositories.usuarios.UsuarioRepository;
import de.pecus.api.services.usuarios.TokenService;
import de.pecus.api.util.GenericExecuteWSUtils;
import de.pecus.api.util.RequestVOUtil;
import de.pecus.api.util.ResponseUtil;
import de.pecus.api.util.ServiceUtil;
import de.pecus.api.util.StringUtil;
import de.pecus.api.util.ValidatorUtil;
import de.pecus.api.vo.EmailVO;
import de.pecus.api.vo.RequestVO;
import de.pecus.api.vo.ResponseVO;
import de.pecus.api.vo.messaging.AsignarTokenFirebaseRequestVO;
import de.pecus.api.vo.messaging.SendActivationCodeRequestVO;
import de.pecus.api.vo.messaging.SendActivationCodeResponseVO;
import de.pecus.api.vo.messaging.SendMailMessageRequestVO;
import de.pecus.api.vo.messaging.SendMailMessageResponseVO;
import de.pecus.api.vo.messaging.SendSmsMessageResponseVO;
import de.pecus.api.vo.messaging.ValidateCodeRequestVO;
import de.pecus.api.vo.messaging.ValidateCodeResponseVO;
import de.pecus.api.vo.messaging.notificationSMS;


/**
 * Clase de logica de negocio para administracionde de token/codigos
 * @author NTT DATA
 */
@Service
public class TokenServiceImpl implements TokenService {
	
	/** Logger */
	private static final SmartLogger LOGGER = SmartLoggerFactory.getLogger(TokenServiceImpl.class);
	
	@Value("${service.client.send-mail}")
	private String sendMailServiceUrl;
	
	@Value("${service.client.send-sms}")
	private String sendSmsServiceUrl;
	
	@Autowired
	private CodigoRepository codigoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	/** Crea un nuevo codigo de activacion
	 * @param request Objeto con parametros de entrada para la generacion del codigo
	 * @return codigo de activacion
	 */
	@Override
	public ResponseVO<SendActivationCodeResponseVO> createCodigoActivacion(RequestVO<SendActivationCodeRequestVO> request) {
		ResponseVO<SendActivationCodeResponseVO> response = new ResponseVO<>();
		
		if (validateCreateActivationCode(request, response)){
			CodigoActivacionDO codigoDO = null;
			// Se busca si existe el email en la tabla de codigos de activacion
			if(!ValidatorUtil.isNullOrEmpty(request.getParameters().getEmail())){
				codigoDO = findByUserId(request.getParameters().getEmail());
			} else if (!ValidatorUtil.isNullOrEmpty(request.getParameters().getTelefono()) && ValidatorUtil.isNull(codigoDO)) {
            	codigoDO = findByUserId(request.getParameters().getTelefono());
            }
			// Se genera el codigo de activacion aleatorio
			String code = StringUtil.generateRandomPassword(5);
			
			try {
				// Si no existe el usuario se crea un nuevo registro
                if (ValidatorUtil.isNull(codigoDO)) {
                	codigoDO = createNewCode(code, request);
                } else { // Si existe solo actualiza el registro con el nuevo codigo
                	codigoDO = updateCode(code, codigoDO, request);
                }
				
				SendActivationCodeResponseVO responseVO = new SendActivationCodeResponseVO();
				responseVO.setIdCodigoActivacion(codigoDO.getId());
				response.setData(responseVO);
				response.setSuccess(true);
			} catch (Exception e) {
				LOGGER.error(request, response, e.getMessage(), e);
				ResponseUtil.addError(request, response, UsuarioBusinessErrors.ACTIVATION_CODE_ERROR);
			}
			if(!ValidatorUtil.isNullOrEmpty(request.getParameters().getEmail())){
				// Se envia la notificacion por email
				sendCodeNotification(request, response, codigoDO, code);
			}
			if(!ValidatorUtil.isNullOrEmpty(request.getParameters().getTelefono())){
				// Se envia la notificacion por telefono
				sendCodeNotificationSms(request, response, codigoDO, code);
			}
			// Si existe algun error al enviar la notificacion se cambia el estatus
			if(!ValidatorUtil.isSuccessfulResponse(response)) {
				response.setSuccess(false);
				response.setData(null);
			}
		}
		return response;
	}

	/** valida un Determinado codigo
	 * @param request Objeto con parametros de entrada para la validacion del codigo
	 * @return objeto con el estatus de la validacion
	 */
	@Override
	public ResponseVO<ValidateCodeResponseVO> validateCodigoActivacion(RequestVO<ValidateCodeRequestVO> request){
		ResponseVO<ValidateCodeResponseVO> response = new ResponseVO<>();
		
		if(validateRequestValidationCode(request, response)) {
			CodigoActivacionDO codigoDO = null;
			// Se busca si existe el email en la tabla de codigos de activacion
			if(!ValidatorUtil.isNullOrEmpty(request.getParameters().getEmail())){
				codigoDO = findByUserId(request.getParameters().getEmail());
			} else if (!ValidatorUtil.isNullOrEmpty(request.getParameters().getTelefono()) && ValidatorUtil.isNull(codigoDO)) {
            	codigoDO = findByUserId(request.getParameters().getTelefono());
            }
            
            if (ValidatorUtil.isNull(codigoDO)) {
				ResponseUtil.addError(request, response, UsuarioBusinessErrors.NOT_FOUND_USUARIO_ERROR);
				return response;
			}
            
            try {
            	ValidateCodeResponseVO responseVO = new ValidateCodeResponseVO();
            	String estadoToken = Boolean.TRUE.equals(codigoDO.getActive()) ? "Activo" : "Inactivo";
            	
	            // Se valida si el codigo en BD esta vigente y si no excede el numero de intentos
	            if  (checkCodeValidity(codigoDO, request , response)) {
	            	// Se actualiza el registro de intentos y  se inactiva el codigo
	            	codigoDO = updateCode(codigoDO, true);
	            	
	            	// Se construye la respuesta final
					responseVO.setEstadoToken(estadoToken);
					responseVO.setIntentos(codigoDO.getIntentosCodigoActivacion());
					responseVO.setVerificacion(true);
					
					response.setData(responseVO);
					response.setSuccess(true);
	            	
	            }else {
	            	return response;
	            }
            }catch(Exception e) {
            	LOGGER.error(request, response, e.getMessage(), e);
				ResponseUtil.addError(request, response, UsuarioBusinessErrors.ACTIVATION_CODE_ERROR);
            }			
		}
		return response;
	}
	
	private boolean checkCodeValidity(CodigoActivacionDO codigoDO, RequestVO<ValidateCodeRequestVO> request,
			ResponseVO<ValidateCodeResponseVO> response) {

		ValidateCodeRequestVO parameters = request.getParameters();

		// Se valida si el codigo esta vigente
		if (!validateExpirationTime(codigoDO.getFechaCreacionCodAct()) || codigoDO.getEstatusCodigoActivacion() != 1
				|| Boolean.FALSE.equals(codigoDO.getActive())) {
			LOGGER.debug(request, UsuarioBusinessErrors.EXPIRED_CODIGO_ACTIVACION);
			ResponseUtil.addError(request, response, UsuarioBusinessErrors.EXPIRED_CODIGO_ACTIVACION);
			updateCode(codigoDO, true);
			return false;
		}

		// Validamos los intentos
		if (codigoDO.getIntentosCodigoActivacion() >= UsuariosDataConstants.CODIGO_ACTIVACION_INTENTOS_LIMITE) {
			LOGGER.debug(request, UsuarioBusinessErrors.EXCEEDED_INTENTS_CODIGO_ACTIVACION);
			ResponseUtil.addError(request, response, UsuarioBusinessErrors.EXCEEDED_INTENTS_CODIGO_ACTIVACION);
			updateCode(codigoDO, true);
			return false;
		}

		// Se valida si el codigo corresponde con el de base de datos
		if (!parameters.getCodigoActivacion().equals(codigoDO.getCodigoActivacion())) {
			LOGGER.debug(request, UsuarioBusinessErrors.INVALID_CODIGO_ACTIVACION);
			ResponseUtil.addError(request, response, UsuarioBusinessErrors.INVALID_CODIGO_ACTIVACION);
			updateCode(codigoDO, false);
			return false;
		}

		return true;
	}

	private boolean validateExpirationTime(Date creationCodeDate) {
		Date expirationDate = DateUtils.addMinutes(creationCodeDate, UsuariosDataConstants.CODIGO_ACTIVACION_LIFETIME);
		Date currentDate = ServiceUtil.getCurrentDate();
		
		return currentDate.before(expirationDate);
	}
	
	/**
	 * Actualiza el token de firebase de un usuario por medio del telefono o email
	 * @param requestVO con los parametros de entrada 
	 * @return Objeto response bandera que indica si se actualizo el token
	 * 
	 */
	@Override
	public ResponseVO<Boolean> asignarTokenFirebase(RequestVO<AsignarTokenFirebaseRequestVO> request) {
		ResponseVO<Boolean> response = new ResponseVO<>();

		// Validamos parametros de entrada
		validateParamsAsignarTokenFirebase(request, response);

		if (ValidatorUtil.isSuccessfulResponse(response)) {
			// Buscamos al usuario por numeor de telefono
			UsuarioDO usuarioDO = usuarioRepository.findByMobileNumberAndLadaPais(UsuariosDataConstants.LADA_MEXICO, request.getParameters().getUserIdMobileNumber());
			if (ValidatorUtil.isNull(usuarioDO) && !ValidatorUtil.isNullOrEmpty(request.getParameters().getUserIdEmail())) {
				// Buscamos al usuario por email
				usuarioDO = usuarioRepository.findByUserIdEmail(request.getParameters().getUserIdEmail());
			}
			
			// Validamos si se encontro al usuario en alguna de las busquedas anteriores
			if(ValidatorUtil.isNull(usuarioDO)) {
				LOGGER.debug(request, UsuarioBusinessErrors.NOT_FOUND_USUARIO_ERROR);
				ResponseUtil.addError(request, response, UsuarioBusinessErrors.NOT_FOUND_USUARIO_ERROR);
			}else {
				// Si se encontro el usuario actualizamos el token y los datos de auditoria
				ServiceUtil.setAuditFields(usuarioDO, request.getToken());
				usuarioDO.setPushProviderToken(request.getParameters().getTokenFirebase());
				usuarioRepository.saveAndFlush(usuarioDO);
				response.setData(Boolean.TRUE);
				response.setSuccess(Boolean.TRUE);				
			}
		}

		return response;
	}
	
	// --------------------------------------------------------------------------------------------
	// ---------------------------------- Metodos Auxiliares --------------------------------------
	// --------------------------------------------------------------------------------------------
	
	/**
	 * @param request
	 * @param response
	 * @return
	 */
	private boolean validateCreateActivationCode(RequestVO<SendActivationCodeRequestVO> request,
			ResponseVO<SendActivationCodeResponseVO> response) {
		SendActivationCodeRequestVO parameters = request.getParameters();
		if (ValidatorUtil.isNull(parameters)) {
			// Si no se han informado los parametros regresa el error y no sigue validando
			ResponseUtil.addError(request, response, GeneralBusinessErrors.REQUIRED_PARAMETERS_ERROR);
			return false;
		}
		if(!ValidatorUtil.isNullOrEmpty(parameters.getEmail()) && !ValidatorUtil.isNullOrEmpty(parameters.getTelefono())) {
			ResponseUtil.addError(request, response, UsuarioBusinessErrors.DATA_OF_CONTACT_ERROR);
		}
		if (ValidatorUtil.isNullOrEmpty(parameters.getEmail()) && ValidatorUtil.isNullOrEmpty(parameters.getTelefono())) {
			ResponseUtil.addError(request, response, UsuarioBusinessErrors.REQUIRED_DATA_OF_CONTACT);
		}else if(!ValidatorUtil.isNullOrEmpty(parameters.getTelefono())){
			if(request.getParameters().getTelefono().length()!=UsuariosDataConstants.PHONE_NUMBER_SIZE) {
				ResponseUtil.addError(request, response, UsuarioBusinessErrors.MIN_SIZE_PHONE_NUMBER);
			}
		}
		if (ValidatorUtil.isNullOrEmpty(parameters.getAplicacion())) {
			ResponseUtil.addError(request, response, UsuarioBusinessErrors.REQUIRED_APLICACION_ERROR);
		}
		return ValidatorUtil.isSuccessfulResponse(response);
	}
	
	/**
	 * Metodo para buscar un registro mediante el email
	 * @param email
	 * @return
	 */
	private CodigoActivacionDO findByUserId(String email) {
		return codigoRepository.findByUserId(email);
	}

	/**
	 * Metodo que crea un nuevo registro en la tabla de Codigo_activacion
	 * @param activationCode
	 * @param request
	 * @return
	 */
	private CodigoActivacionDO createNewCode(String activationCode, RequestVO<SendActivationCodeRequestVO> request) {
		CodigoActivacionDO codigoDO = new CodigoActivacionDO();
		if(!ValidatorUtil.isNullOrEmpty(request.getParameters().getEmail())){
			codigoDO.setUserId(request.getParameters().getEmail());
		}
		
		if(!ValidatorUtil.isNullOrEmpty(request.getParameters().getTelefono())){
			codigoDO.setUserId(request.getParameters().getTelefono());
		}
				
		codigoDO.setCodigoActivacion(activationCode);
		codigoDO.setFechaCreacionCodAct(Date.from(Instant.now()));
		codigoDO.setIntentosCodigoActivacion(0);
		codigoDO.setEstatusCodigoActivacion(1);
		
		ServiceUtil.setAuditFields(codigoDO, request.getToken());			
		
		// Guardamos el usuario en la BD de usuarios
		codigoDO = codigoRepository.saveAndFlush(codigoDO);
				
		return codigoDO;
	}
	
	/**
	 * Metodo que actualiza un registro en la tabla de codigo_activacion
	 * @param activationCode
	 * @param codigoDO
	 * @return
	 */
	private CodigoActivacionDO updateCode(String activationCode, CodigoActivacionDO codigoDO, RequestVO<SendActivationCodeRequestVO> request) {
		// Actualizamos el objeto codigoDO
		codigoDO.setCodigoActivacion(activationCode);
		codigoDO.setFechaCreacionCodAct(ServiceUtil.getCurrentDate());
		codigoDO.setIntentosCodigoActivacion(0);
		codigoDO.setEstatusCodigoActivacion(1);
		ServiceUtil.setAuditFields(codigoDO, request.getToken());
		
		// Guardamos el usuario en la BD de usuarios
		codigoDO = codigoRepository.saveAndFlush(codigoDO);
		
		return codigoDO;
	}
	
	private CodigoActivacionDO updateCode(CodigoActivacionDO codigoDO, boolean cambiaEstado) {
		// Se incrementa el numero de intentos
		int intentos = codigoDO.getIntentosCodigoActivacion();
		intentos ++;
		
		// Se Actualiza el objeto Codigo
		codigoDO.setIntentosCodigoActivacion(intentos);
		codigoDO.setLastModifiedDate(ServiceUtil.getCurrentDate());
		
		if(cambiaEstado) {
			codigoDO.setEstatusCodigoActivacion(0);
			codigoDO.setActive(GeneralConstants.ZERO);
		}
		
		// Guardamos el usuario en la BD de usuarios
		codigoDO = codigoRepository.saveAndFlush(codigoDO);
		
		return codigoDO;
	}
	
	/**
	 * Metodo encargado de enviar la notificacion de codigo de activacion por EMAIL
	 * @param request
	 * @param response
	 * @param usuarioDO
	 * @param password
	 */
	private void sendCodeNotification(RequestVO<SendActivationCodeRequestVO> request, ResponseVO<SendActivationCodeResponseVO> response,
			CodigoActivacionDO codigoDO, String password) {
		if(ValidatorUtil.isSuccessfulResponse(response)) {
			//Asignamos una plantilla por default
			String nombrePlantilla = null;
			// Validamos de que aplicacion proviene la solicitud para determinar el nombre de la plantilla
			switch (AplicacionEnum.valueOf(request.getParameters().getAplicacion())) {
			case PECUS_MOBILE_AS:
				nombrePlantilla = UsuariosDataConstants.NOTIFICACION_AS_CODIGO_ACTIVACION;
				break;
			default:
				nombrePlantilla = UsuariosDataConstants.CODIGO_ACTIVACION;
				break;
			}
			// Se notifica al usuario el codigo de activacion por correo electronico
			SendMailMessageRequestVO sendMailMessageRequestVO = new SendMailMessageRequestVO();
			sendMailMessageRequestVO.setIdNombreNotificacion(nombrePlantilla);
			List<EmailVO> emailVOs = new ArrayList<>();
			EmailVO emailVO = new EmailVO();
			emailVO.setEmail(request.getParameters().getEmail());
			emailVOs.add(emailVO);
			sendMailMessageRequestVO.setTo(emailVOs);
			Map<String, String> parametrosPlantilla = new HashMap<>();
			parametrosPlantilla.put(UsuariosDataConstants.PARAMETRO_PLANTILLA_PASSWORD, password);
			sendMailMessageRequestVO.setParametrosPlantillaMap(parametrosPlantilla);
			
			ResponseVO<SendMailMessageResponseVO> responseNotificationService = GenericExecuteWSUtils.executExchange(
					RequestVOUtil.setNewRequestVO(request, sendMailMessageRequestVO), sendMailServiceUrl,
					HttpMethod.POST.name(), SendMailMessageResponseVO.class);
			
			if(!ValidatorUtil.isSuccessfulResponse(responseNotificationService)) {
				response.setErrors(responseNotificationService.getErrors());
			}			
		}
	}
	
	
	private void sendCodeNotificationSms(RequestVO<SendActivationCodeRequestVO> request, ResponseVO<SendActivationCodeResponseVO> response,
			CodigoActivacionDO codigoDO, String password) {
		if(ValidatorUtil.isSuccessfulResponse(response)) {
			// Se notifica al usuario el codigo de activacion por SMS telefono
			notificationSMS notificationSMS = new notificationSMS();
			notificationSMS.setMessage(UsuariosDataConstants.SMS_CODIGO_ACTIVACION + password);
			notificationSMS.setPhoneNumber(request.getParameters().getTelefono());
			ResponseVO<SendSmsMessageResponseVO> responseNotificationSmsService = GenericExecuteWSUtils.executExchange(
					RequestVOUtil.setNewRequestVO(request, notificationSMS), sendSmsServiceUrl,
					HttpMethod.POST.name(), SendSmsMessageResponseVO.class);
			
			if(!ValidatorUtil.isSuccessfulResponse(responseNotificationSmsService)) {
				response.setErrors(responseNotificationSmsService.getErrors());
			}
		}
	}
	
	/**
	 * Valida la peticion para la Validacion de codigo de activacion
	 * @param request
	 * @param response
	 * @return
	 */
	private boolean validateRequestValidationCode(RequestVO<ValidateCodeRequestVO> request,
			ResponseVO<ValidateCodeResponseVO> response) {
		ValidateCodeRequestVO parameters = request.getParameters();
		if (ValidatorUtil.isNull(parameters)) {
			// Si no se han informado los parametros regresa el error y no sigue validando
			ResponseUtil.addError(request, response, GeneralBusinessErrors.REQUIRED_PARAMETERS_ERROR);
			return false;
		}
		if(!ValidatorUtil.isNullOrEmpty(parameters.getEmail()) && !ValidatorUtil.isNullOrEmpty(parameters.getTelefono())) {
			ResponseUtil.addError(request, response, UsuarioBusinessErrors.DATA_OF_CONTACT_ERROR);
		}
		if (ValidatorUtil.isNullOrEmpty(parameters.getEmail()) && ValidatorUtil.isNullOrEmpty(parameters.getTelefono())) {
			ResponseUtil.addError(request, response, UsuarioBusinessErrors.REQUIRED_DATA_OF_CONTACT);
		}else if(!ValidatorUtil.isNullOrEmpty(parameters.getTelefono())){
			if(request.getParameters().getTelefono().length()!=UsuariosDataConstants.PHONE_NUMBER_SIZE) {
				ResponseUtil.addError(request, response, UsuarioBusinessErrors.MIN_SIZE_PHONE_NUMBER);
			}
		}
		
		if (ValidatorUtil.isNullOrEmpty(parameters.getCodigoActivacion())) {
			ResponseUtil.addError(request, response, UsuarioBusinessErrors.REQUIRED_CODIGO_ACTIVACION_ERROR);
		}
		return ValidatorUtil.isSuccessfulResponse(response);
	}
	
	/**
	 * Metodo para la validacion de parametros de la operacion findByEmailAndPhone
	 * @param request
	 * @param response
	 */
	private void validateParamsAsignarTokenFirebase(RequestVO<AsignarTokenFirebaseRequestVO> request,
			ResponseVO<Boolean> response) {

		if (ValidatorUtil.isNull(request.getParameters())) {
			// Si no se ha informado ningun parametro regresa el error y no sigue validando
			LOGGER.debug(request, GeneralBusinessErrors.REQUIRED_PARAMETERS_ERROR);
			ResponseUtil.addError(request, response, GeneralBusinessErrors.REQUIRED_PARAMETERS_ERROR);
		} else {
			if (ValidatorUtil.isNullOrEmpty(request.getParameters().getUserIdMobileNumber())
					&& ValidatorUtil.isNullOrEmpty(request.getParameters().getUserIdEmail())) {
				// Si no se ha informado ningun parametro regresa el error y no sigue validando
				ResponseUtil.addError(request, response, UsuarioBusinessErrors.REQUIRED_USER_ID_MOBILE_NUMBER_ERROR);
				LOGGER.debug(request, UsuarioBusinessErrors.REQUIRED_USER_ID_MOBILE_NUMBER_ERROR);
			}
			
			if (ValidatorUtil.isNullOrEmpty(request.getParameters().getTokenFirebase())) {
				// Si no se ha informado ningun parametro regresa el error y no sigue validando
				ResponseUtil.addError(request, response, UsuarioBusinessErrors.REQUIRED_TOKEN_ERROR);
				LOGGER.debug(request, UsuarioBusinessErrors.REQUIRED_TOKEN_ERROR);
			}
		}
	}
}
