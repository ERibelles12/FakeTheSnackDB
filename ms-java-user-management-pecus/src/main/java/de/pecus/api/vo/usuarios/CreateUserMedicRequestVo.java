package de.pecus.api.vo.usuarios;

import java.io.Serializable;

public class CreateUserMedicRequestVo implements Serializable {

	/**
	 * Serial Version UID de la clase
	 */
	private static final long serialVersionUID = 7636032653899524207L;

	
	private Long idMedico;

	private Long idPersona;
	
	private Long idPaciente;

	private String email;

	private String password;

	private String telefono;

	private String aplicacion;

	/**
	 * @return the idMedico
	 */
	public Long getIdMedico() {
		return idMedico;
	}

	/**
	 * @param idMedico the idMedico to set
	 */
	public void setIdMedico(Long idMedico) {
		this.idMedico = idMedico;
	}

	/**
	 * @return the idPersona
	 */
	public Long getIdPersona() {
		return idPersona;
	}

	/**
	 * @param idPersona the idPersona to set
	 */
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono the telefono to set
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

	/**
	 * @return the idPaciente
	 */
	public Long getIdPaciente() {
		return idPaciente;
	}

	/**
	 * @param idPaciente the idPaciente to set
	 */
	public void setIdPaciente(Long idPaciente) {
		this.idPaciente = idPaciente;
	}
	
	
	

}
