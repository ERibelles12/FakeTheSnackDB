/**
 * 
 */
package de.pecus.api.vo.messaging;

import java.io.Serializable;

/**
 * @author a18797
 *
 */
public class AsignarTokenFirebaseRequestVO implements Serializable {
	
	/**
	 * Serial Version UID de la clase
	 */
	private static final long serialVersionUID = -3683087761805297561L;

	/** Identificador de correo electronico a validar **/
	private String userIdEmail;
	
	/** Numero de telefono a buscar **/
	private String userIdMobileNumber;
	
	/** Token firebase para notificaciones PUSH **/
	private String tokenFirebase;

	/**
	 * @return the userIdEmail
	 */
	public String getUserIdEmail() {
		return userIdEmail;
	}

	/**
	 * @param userIdEmail the userIdEmail to set
	 */
	public void setUserIdEmail(String userIdEmail) {
		this.userIdEmail = userIdEmail;
	}

	/**
	 * @return the userIdMobileNumber
	 */
	public String getUserIdMobileNumber() {
		return userIdMobileNumber;
	}

	/**
	 * @param userIdMobileNumber the userIdMobileNumber to set
	 */
	public void setUserIdMobileNumber(String userIdMobileNumber) {
		this.userIdMobileNumber = userIdMobileNumber;
	}

	/**
	 * @return the tokenFirebase
	 */
	public String getTokenFirebase() {
		return tokenFirebase;
	}

	/**
	 * @param tokenFirebase the tokenFirebase to set
	 */
	public void setTokenFirebase(String tokenFirebase) {
		this.tokenFirebase = tokenFirebase;
	}
}
