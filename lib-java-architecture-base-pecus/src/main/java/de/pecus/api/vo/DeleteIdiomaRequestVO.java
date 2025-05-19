package de.pecus.api.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/******************** FIN SECCION IMPORTS ************************************/

/**
 * Entidad que contiene los idiomas
 * @author jose.ribelles
 * @version 1.0
 * @created 14-ago.-2019 08:34:11 a. m.
 */
@JsonInclude(Include.NON_NULL)
public class DeleteIdiomaRequestVO {

	/**
	 * Identificador unico de registro
	 */
	private Long id;

	public DeleteIdiomaRequestVO(){

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

}