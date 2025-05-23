package de.pecus.api.vo.usuarios;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 
 * @param idNombre
 */
 

@JsonInclude(Include.NON_NULL)
public class AplicacionVO {
	
	private Long id;
	private String idNombre;
	
	public AplicacionVO() {
		
	}
	
	public AplicacionVO(Long id, String idNombre) {
		super();
		this.id = id;
		this.idNombre = idNombre;
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
	
}
