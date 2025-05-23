package de.pecus.api.vo.usuarios;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * VO para la creacion de una relacion entre usuario y rol
 * @author Luis Enrique Sanchez Santiago
 *
 */
@JsonInclude(Include.NON_NULL)
public class CreateUsuarioRolRequestVO {
	/**
	 * Identificador de usuario
	 */
	private Long idUser;
	/**
	 * Identificador de rol
	 */
	private Long idRol;
	/**
	 * Fecha inicio
	 */
	private Date fechaInicio;
	/**
	 * Identificador de usuario
	 * @return the idUser
	 */
	public Long getIdUser() {
		return idUser;
	}
	/**
	 * Identificador de usuario
	 * @param idUser the idUser to set
	 */
	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}
	/**
	 * Identificador de rol
	 * @return the idRol
	 */
	public Long getIdRol() {
		return idRol;
	}
	/**
	 * Identificador de rol
	 * @param idRol the idRol to set
	 */
	public void setIdRol(Long idRol) {
		this.idRol = idRol;
	}
	/**
	 * Fecha inicio
	 * @return the fechaInicio
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}
	/**
	 * Fecha inicio
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	
}
