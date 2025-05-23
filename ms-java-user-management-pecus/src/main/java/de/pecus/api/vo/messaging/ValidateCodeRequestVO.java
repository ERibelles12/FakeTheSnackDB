package de.pecus.api.vo.messaging;

public class ValidateCodeRequestVO {
	
	/**
	 * correo electronico del usuario
	 */
	private String email;
	
	/**
	 * correo telefono del usuario
	 */
	private String telefono;
	
	/**
	 * codigo de activacion
	 */
	private String codigoActivacion;

	/**
	 * 
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 *
	 * @return codigo de activacion
	 */
	public String getCodigoActivacion() {
		return codigoActivacion;
	}

	/**
	 * 
	 * @param codigoActivacion
	 */
	public void setCodigoActivacion(String codigoActivacion) {
		this.codigoActivacion = codigoActivacion;
	}
	
}
