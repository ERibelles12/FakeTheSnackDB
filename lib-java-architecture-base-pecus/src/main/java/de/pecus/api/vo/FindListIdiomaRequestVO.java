package de.pecus.api.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/******************** FIN SECCION IMPORTS ************************************/

/**
 * Entidad que contiene los idiomas
 * @author jose.ribelles
 * @version 1.0
 * @created 14-ago.-2019 08:34:24 a. m.
 */
@JsonInclude(Include.NON_NULL)
public class FindListIdiomaRequestVO {

	/**
	 * Nombre del continente
	 */
	private String idNombre;

	public FindListIdiomaRequestVO(){

	}

	public String getIdNombre() {
		return idNombre;
	}

	public void setIdNombre(String idNombre) {
		this.idNombre = idNombre;
	}
	
	

}