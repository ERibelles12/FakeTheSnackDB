package de.pecus.api.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import de.pecus.api.vo.ResponseVO;
import de.pecus.api.vo.messaging.AsignarTokenFirebaseRequestVO;
import de.pecus.api.vo.messaging.SendActivationCodeRequestVO;
import de.pecus.api.vo.messaging.SendActivationCodeResponseVO;
import de.pecus.api.vo.messaging.ValidateCodeRequestVO;
import de.pecus.api.vo.messaging.ValidateCodeResponseVO;

public interface TokenController {
	
	/**
	 * Operation: Create, Method: POST, Description: Envia un código de activacion
	 * 
	 * @param headers
	 * @param 
	 * @return
	 */
	ResponseEntity<ResponseVO<SendActivationCodeResponseVO>> crearCodigoActivacion(Map<String, String> headers,
			SendActivationCodeRequestVO sendAcivationCodeRequestVO);

	/**
	 * Operation: Update, Method: PUT, Description: Valida un codigo de activacion
	 * 
	 * @param headers
	 * @return
	 */
	ResponseEntity<ResponseVO<ValidateCodeResponseVO>> validarCodigoActivacion(Map<String,String> headers,
			ValidateCodeRequestVO validateCodeRequestVO);
	
	/**
	 * Actualiza el token para las notificaiones PUSH de un usuario
	 * 
	 * @param headers
	 * @param asignarTokenFirebaseRequestVO
	 * @return
	 */
	ResponseEntity<ResponseVO<Boolean>> asignarTokenFirebase(Map<String, String> headers,
			AsignarTokenFirebaseRequestVO asignarTokenFirebaseRequestVO);

}
