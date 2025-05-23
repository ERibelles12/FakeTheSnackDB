package de.pecus.api.vo.usuarios;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * VO para Idioma
 */
@JsonInclude(Include.NON_NULL)
public class IdiomaVO {

	private Long id;
	private String idNombre;
	private String descripcion;
	
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
