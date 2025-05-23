package de.pecus.api.vo.messaging;

public class SendActivationCodeRequestVO {
	
	/**
	 * Correo electronico del usuario
	 */
	private String email;
	
	/**
	 * telefono del usuario
	 */
	private String telefono;
	
	private String aplicacion;
	
	/**
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * @return telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * @return the aplicacion
	 */
	public String getAplicacion() {
		return aplicacion;
	}

	/**
	 * @param aplicacion the aplicacion to set
	 */
	public void setAplicacion(String aplicacion) {
		this.aplicacion = aplicacion;
	}

}
