package de.pecus.api.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import de.pecus.api.vo.ResponseVO;
import de.pecus.api.vo.usuarios.ResetPasswordRequestVO;
import de.pecus.api.vo.usuarios.UpdatePasswordRequestVO;

public interface PasswordUsrController {
	
	/**
	 * Actualiza el password de un usuario.
	 * 
	 * @param request Objeto con el parametro de entrada email o telefono
	 * @return Objeto response con direccion de correo
	 */
	ResponseEntity<ResponseVO<String>> updatePassword(Map<String, String> headers,
			UpdatePasswordRequestVO updatePasswordRequestVO);
	
	/**
	 * Metodo para resetear el password de un usuario
	 * 
	 * @param headers
	 * @param resetPasswordRequestVO
	 * @return
	 */
	ResponseEntity<ResponseVO<String>> resetPassword(Map<String, String> headers,
			ResetPasswordRequestVO resetPasswordRequestVO);

}
