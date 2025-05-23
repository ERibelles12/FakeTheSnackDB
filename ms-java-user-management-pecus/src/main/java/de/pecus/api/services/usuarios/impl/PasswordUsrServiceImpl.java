package de.pecus.api.services.usuarios.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import de.pecus.api.annotation.Auditable;
import de.pecus.api.constant.UsuariosDataConstants;
import de.pecus.api.entities.UsuarioDO;
import de.pecus.api.enums.arqcomponentes.AplicacionEnum;
import de.pecus.api.error.GeneralBusinessErrors;
import de.pecus.api.error.UsuarioBusinessErrors;
import de.pecus.api.log.SmartLogger;
import de.pecus.api.log.SmartLoggerFactory;
import de.pecus.api.repositories.usuarios.UsuarioRepository;
import de.pecus.api.security.services.PasswordService;
import de.pecus.api.services.usuarios.PasswordUsrService;
import de.pecus.api.util.GenericExecuteWSUtils;
import de.pecus.api.util.RequestVOUtil;
import de.pecus.api.util.ResponseUtil;
import de.pecus.api.util.ServiceUtil;
import de.pecus.api.util.StringUtil;
import de.pecus.api.util.ValidatorUtil;
import de.pecus.api.vo.EmailVO;
import de.pecus.api.vo.RequestVO;
import de.pecus.api.vo.ResponseErrorVO;
import de.pecus.api.vo.ResponseVO;
import de.pecus.api.vo.messaging.Message;
import de.pecus.api.vo.messaging.NotificationUserVO;
import de.pecus.api.vo.messaging.SendMailMessageRequestVO;
import de.pecus.api.vo.messaging.SendMailMessageResponseVO;
import de.pecus.api.vo.messaging.SendPushMessageRequestVO;
import de.pecus.api.vo.messaging.SendPushMessageResponseVO;
import de.pecus.api.vo.usuarios.ResetPasswordRequestVO;
import de.pecus.api.vo.usuarios.UpdatePasswordRequestVO;

/**
 * Clase de logica de negocio para administracionde contraseñas
 * 
 * @author NTT DATA
 *
 */
@Service
public class PasswordUsrServiceImpl implements PasswordUsrService {

	/** Logger */
	private static final SmartLogger LOGGER = SmartLoggerFactory.getLogger(PasswordUsrServiceImpl.class);
	
	@Autowired
	private PasswordService passwordService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Value("${service.client.send-mail}")
	private String sendMailServiceUrl;
	
	@Value("${service.client.send-push}")
	private String sendPushServiceUrl;

	/**
	 * Actualiza el password de un usuario con una contrasenia generada
	 * aleatoriamente y lo envia por correo electronico
	 * 
	 * @param request Objeto con el parametro de entrada email
	 * @return Objeto response con direccion de correo
	 */
	@Override
	public ResponseVO<String> resetPassword(RequestVO<ResetPasswordRequestVO> request) {
		ResponseVO<String> response = new ResponseVO<>();

		if (validateResetPassword(request, response)) {

			UsuarioDO usuarioDO = exists(request.getParameters().getUserId());

			if (ValidatorUtil.isNull(usuarioDO)) {
				ResponseUtil.addError(request, response, UsuarioBusinessErrors.NOT_FOUND_USUARIO_ERROR);
				return response;
			}
			// Se genera password aleatorio
			String password = StringUtil.generateRandomPassword(8);
			try {
				// Se actualiza el password del usuario
				usuarioDO.setPasswordStatus(UsuariosDataConstants.ESTATUS_CAMBIO_PASSWORD);
				usuarioDO.setPassword(passwordService.encryptPasswordSHA256(password));
				ServiceUtil.setAuditFields(usuarioDO, request.getToken());
				usuarioDO = usuarioRepository.saveAndFlush(usuarioDO);
				response.setData(usuarioDO.getUserIdEmail());
				response.setSuccess(Boolean.TRUE);				
			} catch (Exception e) {
				LOGGER.error(request, response, e.getMessage(), e);
				ResponseUtil.addError(request, response, UsuarioBusinessErrors.RESET_PASSWORD_ERROR);
			}
			// Se envia la notificacion por email
			sendEmailNotification(request, response, usuarioDO, password);
			// Se envia la notificacion por push
			sendPushNotification(request, response, usuarioDO);
		}
		return response;
	}

	/**
	 * Actualiza el password de un usuario aleatoriamente y lo envia por correo
	 * electronico
	 * 
	 * @param request Objeto con el parametro de entrada email o telefono
	 * @return Objeto response con direccion de correo
	 */
	@Override
	@Auditable
	public ResponseVO<String> updatePassword(RequestVO<UpdatePasswordRequestVO> request) {
		ResponseVO<String> response = new ResponseVO<>();

		if (validateUpdatePassword(request, response)) {

			UsuarioDO usuarioDO = exists(request.getParameters().getUserId());

			if (ValidatorUtil.isNull(usuarioDO)) {
				ResponseUtil.addError(request, response, UsuarioBusinessErrors.NOT_FOUND_USUARIO_ERROR);
				return response;
			}else {
				String passwordNuevo = request.getParameters().getNewPassword();
				if (ValidatorUtil.isNull(request.getParameters().getIsMobile()) || !request.getParameters().getIsMobile()) {
					passwordNuevo = passwordService.decryptPasswordAES(passwordNuevo);
				}
				
				// Si es valido se actualiza el password
				usuarioDO.setPassword(passwordService.encryptPasswordSHA256(passwordNuevo));
				usuarioDO.setPasswordStatus(UsuariosDataConstants.ESTATUS_PASSWORD_ACTUALIZADO);
				ServiceUtil.setAuditFields(usuarioDO, request.getToken());
				usuarioDO = usuarioRepository.saveAndFlush(usuarioDO);
				
				response.setData(usuarioDO.getUserIdEmail());
				response.setSuccess(Boolean.TRUE);
			}

		}
		return response;
	}
	
	// --------------------------------------------------------------------------------------------
	// ---------------------------------- Metodos Auxiliares --------------------------------------
	// --------------------------------------------------------------------------------------------
	
	/**
	 * Metodo que valida los parametros de entrada del request de reset password
	 * 
	 * @param request  Objeto con el correo electronico
	 * @param response Respuesta donde se regresan los errores de validacion
	 * @return true si los parametros validos, false en caso contrario
	 */
	private boolean validateResetPassword(RequestVO<ResetPasswordRequestVO> request, ResponseVO<String> response) {
		ResetPasswordRequestVO parameters = request.getParameters();
		// validar el campo obligatorio email
		if (ValidatorUtil.isNull(parameters)) {
			// Si no se ha informado ningun parametro regresa el error y no sigue validando
			LOGGER.debug(request, GeneralBusinessErrors.REQUIRED_PARAMETERS_ERROR);
			return false;
		}
		if (ValidatorUtil.isNullOrEmpty(parameters.getUserId())) {
			ResponseUtil.addError(request, response, UsuarioBusinessErrors.REQUIRED_USER_ID_EMAIL_ERROR);
		}
		if (ValidatorUtil.isNullOrEmpty(parameters.getAplicacion())) {
			ResponseUtil.addError(request, response, UsuarioBusinessErrors.REQUIRED_APLICACION_ERROR);
		}
		return ValidatorUtil.isSuccessfulResponse(response);
	}
	
	/**
	 * Metodo encargado de validar la existencia del usuario por su identificador, puede ser email o numero de telefono
	 * @param request
	 * @return
	 */
	private UsuarioDO exists(String userId) {

		// Buscamos al usuario por email
		UsuarioDO usuarioDO = usuarioRepository.findByUserIdEmail(userId);

		if (ValidatorUtil.isNull(usuarioDO)) {

			// Buscamos al usuario por numero telefonico
			usuarioDO = usuarioRepository.findByMobileNumberAndLadaPais(UsuariosDataConstants.LADA_MEXICO, userId);
		}

		return usuarioDO;
	}
	
	/**
	 * Metodo encargado de enviar la notificacion de reset password por canal EMAIL
	 * @param request
	 * @param response
	 * @param usuarioDO
	 * @param password
	 */
	private void sendEmailNotification(RequestVO<ResetPasswordRequestVO> request, ResponseVO<String> response,
			UsuarioDO usuarioDO, String password) {
		if(ValidatorUtil.isSuccessfulResponse(response)) {
			//Asignamos una plantilla por default
			String nombrePlantilla = null;
			// Validamos de que aplicacion proviene la solicitud para determinar el nombre de la plantilla
			switch (AplicacionEnum.valueOf(request.getParameters().getAplicacion())) {
			case PECUS_MOBILE_AS:
				nombrePlantilla = UsuariosDataConstants.NOTIFICACION_AS_EMAIL_RESET_PASSWORD;
				break;
			default:
				nombrePlantilla = UsuariosDataConstants.NOTIFICACION_EMAIL_RESET_PASSWORD;
				break;
			}
			// Se notifica al usuario el password temporal por correo electronico
			SendMailMessageRequestVO sendMailMessageRequestVO = new SendMailMessageRequestVO();
			sendMailMessageRequestVO.setIdNombreNotificacion(nombrePlantilla);
			List<EmailVO> emailVOs = new ArrayList<EmailVO>();
			EmailVO emailVO = new EmailVO();
			emailVO.setEmail(usuarioDO.getUserIdEmail());
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
	
	/**
	 * Metodo encargado de enviar la notificación de reseteo de password por canal PUSH
	 * @param request
	 * @param response
	 * @param usuarioDO
	 */
	private void sendPushNotification(RequestVO<ResetPasswordRequestVO> request, ResponseVO<String> response,
			UsuarioDO usuarioDO ) {
		if(ValidatorUtil.isSuccessfulResponse(response)) {
			//Se notifica el reseteo de password por PUSH notification
			SendPushMessageRequestVO sendPushMessageRequestVO = new SendPushMessageRequestVO();
			Message message = new Message();
			message.setTitle("Reset password");
			message.setText("El password se reseteo de manera exitosa.");
			sendPushMessageRequestVO.setMessage(message);
			List<NotificationUserVO> listUserNotification = new ArrayList<NotificationUserVO>();
			NotificationUserVO notificationUserVO = new NotificationUserVO();
			notificationUserVO.setNotificationToken(usuarioDO.getPushProviderToken());
			notificationUserVO.setUserId(usuarioDO.getId());
			listUserNotification.add(notificationUserVO);
			sendPushMessageRequestVO.setNotificationUserVOs(listUserNotification);
			
			ResponseVO<SendPushMessageResponseVO> responseNotificationService = GenericExecuteWSUtils.executExchange(
					RequestVOUtil.setNewRequestVO(request, sendPushMessageRequestVO), sendPushServiceUrl,
					HttpMethod.POST.name(), SendPushMessageResponseVO.class);
			
			if(!ValidatorUtil.isSuccessfulResponse(responseNotificationService)) {
				for(ResponseErrorVO responseErrorVO : responseNotificationService.getErrors()) {
					LOGGER.error(request, responseNotificationService, responseErrorVO.getMessage(), responseErrorVO.getException());
				}
			}
		}
	}
	
	/**
	 * Metodo que valida los parametros de entrada del request de update password
	 * 
	 * @param request  Objeto con el correo electronico, el password anterior y el
	 *                 nuevo
	 * @param response Respuesta donde se regresan los errores de validacion
	 * @return true si los parametros validos, false en caso contrario
	 */
	private boolean validateUpdatePassword(RequestVO<UpdatePasswordRequestVO> request, ResponseVO<String> response) {
		UpdatePasswordRequestVO parameters = request.getParameters();
		// validar los campos obligatorios
		if (ValidatorUtil.isNull(parameters)) {
			// Si no se ha informado ningun parametro regresa el error y no sigue validando
			LOGGER.debug(request, GeneralBusinessErrors.REQUIRED_PARAMETERS_ERROR);
		}
		if (ValidatorUtil.isNullOrEmpty(parameters.getUserId())) {
			ResponseUtil.addError(request, response, UsuarioBusinessErrors.REQUIRED_EMAIL_OR_PHONE);
		}
		if (ValidatorUtil.isNullOrEmpty(parameters.getNewPassword())) {
			ResponseUtil.addError(request, response, UsuarioBusinessErrors.REQUIRED_PASSWORD_NUEVO_ERROR);
		}

		return ValidatorUtil.isSuccessfulResponse(response);
	}	
}
