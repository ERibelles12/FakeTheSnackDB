package de.pecus.api.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/******************** FIN SECCION IMPORTS ************************************/

/**
 * Entidad que contiene los idiomas
 * @author jose.ribelles
 * @version 1.0
 * @created 14-ago.-2019 08:33:58 a. m.
 */
@JsonInclude(Include.NON_NULL)
public class CreateIdiomaRequestVO {

	/**
	 * Nombre del idioma
	 */
	private String idNombre;

	/**
	 * Descripcion del idioma
	 */
	private String descripcion;

	public CreateIdiomaRequestVO(){

	}

	public String getIdNombre() {
		return idNombre;
	}

	public void setIdNombre(String idNombre) {
		this.idNombre = idNombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}