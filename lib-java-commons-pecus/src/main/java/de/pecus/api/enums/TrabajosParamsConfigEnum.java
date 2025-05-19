package de.pecus.api.enums;

/**
 * Enum de Estatus Archivo
 * 
 * Enumera los distintos Estatus de Archivo
 * 
 * @author Alfredo Martinez Ramirez
 */
public enum TrabajosParamsConfigEnum {
	
	URL_CREATE_JOB("URL_CREATE_JOB"),
	TIPO_ARCHIVO("TIPO_ARCHIVO"),
	COMPONENTE("COMPONENTE");
	
	
	private String param;
	
	private TrabajosParamsConfigEnum( String param ) {
		this.param = param;
	}
	
	public String getParam() {
		return param;
	}
}
