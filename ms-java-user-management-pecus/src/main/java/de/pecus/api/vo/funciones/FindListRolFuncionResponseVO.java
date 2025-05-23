package de.pecus.api.vo.funciones;

/**
 * 
 * @author Jose_Luis_Garcia
 *
 *         Clase con los parametros de entrada a la invocacion del metodo create
 *         de la case EventType
 *
 */
public class FindListRolFuncionResponseVO {

	// id
	private Long id;
	
	private Long idRol;
	
	private String idNombreRol;
	
	private Long idFuncion;
	
	private String idNombreFuncion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdRol() {
		return idRol;
	}

	public void setIdRol(Long idRol) {
		this.idRol = idRol;
	}

	public String getIdNombreRol() {
		return idNombreRol;
	}

	public void setIdNombreRol(String idNombreRol) {
		this.idNombreRol = idNombreRol;
	}

	public Long getIdFuncion() {
		return idFuncion;
	}

	public void setIdFuncion(Long idFuncion) {
		this.idFuncion = idFuncion;
	}

	public String getIdNombreFuncion() {
		return idNombreFuncion;
	}

	public void setIdNombreFuncion(String idNombreFuncion) {
		this.idNombreFuncion = idNombreFuncion;
	}
	
	
	
}
