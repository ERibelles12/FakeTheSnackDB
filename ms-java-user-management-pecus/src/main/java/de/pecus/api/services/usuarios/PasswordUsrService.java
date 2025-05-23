package de.pecus.api.services.usuarios;

import de.pecus.api.vo.RequestVO;
import de.pecus.api.vo.ResponseVO;
import de.pecus.api.vo.usuarios.ResetPasswordRequestVO;
import de.pecus.api.vo.usuarios.UpdatePasswordRequestVO;

/**
 * Interfaz para administracion de contraseñas
 * 
 * @author NTT DATA
 *
 */
public interface PasswordUsrService {

	/**
	 * Actualiza el password de un usuario con una contrasenia generada aleatoriamente y lo 
	 * envia por correo electronico
	 * @param request Objeto con el parametro de entrada email
	 * @return Objeto response con direccion de correo
	 */
	ResponseVO<String> resetPassword(RequestVO<ResetPasswordRequestVO> request);
	
	/**
	 * Actualiza el password de un usuario con una contrasenia generada aleatoriamente y lo 
	 * envia por correo electronico
	 * @param request Objeto con el parametro de entrada email
	 * @return Objeto response con direccion de correo
	 */
	ResponseVO<String> updatePassword(RequestVO<UpdatePasswordRequestVO> request);
}
