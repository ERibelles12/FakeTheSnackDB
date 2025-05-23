package de.pecus.api.vo.personas;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/******************** FIN SECCION IMPORTS ************************************/

/**
 * Entidad que tiene los datos de la persona.
 * @author jose_
 * @version 1.0
 * @created 08-ago.-2019 11:05:01 a. m.
 */
@JsonInclude(Include.NON_NULL)
public class FindDetailPersonaResponseVO {

	private Long id;
	private String nombre;
	private String primerApellido;
	private String segundoApellido;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date fechaNacimiento;
	private String genero;
	private String idFiscal;
	private String email;
	private String region;
	private String numero;
	private boolean privacidad;
	private Long idUsuario;
	private String emailFiscal;
	private Long idUsoCfdi;
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public boolean isPrivacidad() {
		return privacidad;
	}

	public void setPrivacidad(boolean privacidad) {
		this.privacidad = privacidad;
	}

	public FindDetailPersonaResponseVO(){

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getSegundoApellido() {
		return segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}
	
	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getIdFiscal() {
		return idFiscal;
	}

	public void setIdFiscal(String idFiscal) {
		this.idFiscal = idFiscal;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * @return the emailFiscal
	 */
	public String getEmailFiscal() {
		return emailFiscal;
	}

	/**
	 * @param emailFiscal the emailFiscal to set
	 */
	public void setEmailFiscal(String emailFiscal) {
		this.emailFiscal = emailFiscal;
	}

	/**
	 * @return the idUsoCfdi
	 */
	public Long getIdUsoCfdi() {
		return idUsoCfdi;
	}

	/**
	 * @param idUsoCfdi the idUsoCfdi to set
	 */
	public void setIdUsoCfdi(Long idUsoCfdi) {
		this.idUsoCfdi = idUsoCfdi;
	}

	
	

}