package de.pecus.api.enums;

/**
 * Enum de Estatus Archivo
 * 
 * Enumera los distintos Estatus de Archivo
 * 
 * @author Alfredo Martinez Ramirez
 */
public enum EstatusTrabajoEnum {
	
	PROGRAMADO("PROGRAMADO"),
	EJECUTANDO("EJECUTANDO"),
	FINALIZADO("FINALIZADO"),
	FINALIZADO_ERROR("COMPLETADO_ERROR"),
	PAUSADO("PAUSADO"),
	ELIMINADO("ELIMINADO");
	
	
	private String idName;
	
	private EstatusTrabajoEnum( String idName ) {
		this.idName = idName;
	}
	
	public String getIdName() {
		return idName;
	}
}
