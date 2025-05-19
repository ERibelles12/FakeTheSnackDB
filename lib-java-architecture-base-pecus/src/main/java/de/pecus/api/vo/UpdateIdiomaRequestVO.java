package de.pecus.api.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/******************** FIN SECCION IMPORTS ************************************/

/**
 * Entidad que contiene los idiomas
 * @author jose.ribelles
 * @version 1.0
 * @created 14-ago.-2019 08:34:05 a. m.
 */
@JsonInclude(Include.NON_NULL)
public class UpdateIdiomaRequestVO {

	/**
	 * Identificador unico de registro
	 */
	private Long id;
	/**
	 * Nombre del idioma
	 */
	private String idNombre;
	
	/**
	 * Descripcion del idioma
	 */
	private String descripcion;

	public UpdateIdiomaRequestVO(){

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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