package de.pecus.api.vo.usuarios;

import java.io.Serializable;

public class CreateUsuarioMobileRequestVO implements Serializable {

	/**
	 * Serial Version UID de la clase
	 */
	private static final long serialVersionUID = 7636032653899524207L;

	private Long idPaciente;
	private Long idPersona;
	private String email;
	private String password;
	private String tokenFirebase;
	private String nombrePaciente;
	private String apellidoPaternoPaciente;
	private String apellidoMaternoPaciente;
	private String fechaNacimiento;
	private String genero;
	private String telefono;
	private String aplicacion;
	
	/**
	 * Obtiene el id del Paciente
	 * @return idPaciente
	 */
	public Long getIdPaciente() {
		return idPaciente;
	}
	
	/**
	 * @param idPaciente
	 */
	public void setIdPaciente(Long idPaciente) {
		this.idPaciente = idPaciente;
	}
	
	/**
	 * Obtiene el id de la Persona
	 * @return idPersona
	 */
	public Long getIdPersona() {
		return idPersona;
	}
	
	/**
	 * @param idPersona
	 */
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}
	
	/**
	 * Obtiene el email
	 * @return
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
	 * Obtiene la contrasena
	 * @return contrasena/password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Obtiene el token de firebase
	 * @return tokenFirebase
	 */
	public String getTokenFirebase() {
		return tokenFirebase;
	}
	
	/**
	 * @param tokenFirebase
	 */
	public void setTokenFirebase(String tokenFirebase) {
		this.tokenFirebase = tokenFirebase;
	}
	
	/**
	 * Ontiene el Nombre del paciente
	 * @return nombrePaciente
	 */
	public String getNombrePaciente() {
		return nombrePaciente;
	}
	
	/**
	 * @param nombrePaciente
	 */
	public void setNombrePaciente(String nombrePaciente) {
		this.nombrePaciente = nombrePaciente;
	}
	
	/**
	 * Obtiene el Apellido Paterno del paciente
	 * @return apellidoPaternoPaciente
	 */
	public String getApellidoPaternoPaciente() {
		return apellidoPaternoPaciente;
	}
	
	/**
	 * @param apellidoPaternoPaciente
	 */
	public void setApellidoPaternoPaciente(String apellidoPaternoPaciente) {
		this.apellidoPaternoPaciente = apellidoPaternoPaciente;
	}
	
	/** Obtiene el Apellido Materno del Paciente 
	 * @return apellidoMaternoPaciente
	 */
	public String getApellidoMaternoPaciente() {
		return apellidoMaternoPaciente;
	}
	
	/**
	 * @param apellidoMaternoPaciente
	 */
	public void setApellidoMaternoPaciente(String apellidoMaternoPaciente) {
		this.apellidoMaternoPaciente = apellidoMaternoPaciente;
	}
	
	/**
	 * Obtiene la fecha de nacimiento
	 * @return fechaNacimiento
	 */
	public String getFechaNacimiento() {
		return fechaNacimiento;
	}
	
	/**
	 * @param fechaNacimiento
	 */
	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	
	/**
	 * Obtiene el genero
	 * @return genero
	 */
	public String getGenero() {
		return genero;
	}
	
	/**
	 * @param genero
	 */
	public void setGenero(String genero) {
		this.genero = genero;
	}
	
	/**
	 * Obtiene el numero de telefono
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
