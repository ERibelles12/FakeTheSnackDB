/**
 * 
 */
package de.pecus.api.vo.usuarios;

/**
 * @author Luis Enrique Sanchez Santiago
 *
 */
public class FindUserByEmailAndPhoneNumberRequestVO {

	/** Identificador de correo electronico a validar **/
	private String userIdEmail;
	
	/** Numero de telefono a buscar **/
	private String userIdMobileNumber;

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

}
