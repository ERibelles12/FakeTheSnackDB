package de.pecus.api.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ArchivoBase64VO {

	/**
	 * Nombre original del archivo
	 */
	private String nombre;
	
	/**
	 * Contenido en base 64
	 */
	private String contenidoBase64;

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param idNombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the contenidoBase64
	 */
	public String getContenidoBase64() {
		return contenidoBase64;
	}

	/**
	 * @param contenidoBase64 the contenidoBase64 to set
	 */
	public void setContenidoBase64(String contenidoBase64) {
		this.contenidoBase64 = contenidoBase64;
	}

}
