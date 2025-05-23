package de.pecus.api.vo.funciones;

/**
 * 
 * @author Jose_Luis_Garcia
 *
 *	Clase con los parametros de entrada a la invocacion del metodo 
 *  findDetail de la case EventType
 *
 */
public class FindListFuncionRequestVO {

	private Long idAplicacion;

	private String idNombre;
	
	private Long idRol;
	
	private String Asignados;

	public Long getIdAplicacion() {
		return idAplicacion;
	}

	public void setIdAplicacion(Long idAplicacion) {
		this.idAplicacion = idAplicacion;
	}

	public String getIdNombre() {
		return idNombre;
	}

	public void setIdNombre(String idNombre) {
		this.idNombre = idNombre;
	}

	public Long getIdRol() {
		return idRol;
	}

	public void setIdRol(Long idRol) {
		this.idRol = idRol;
	}

	public String getAsignados() {
		return Asignados;
	}

	public void setAsignados(String asignados) {
		Asignados = asignados;
	}
	
}
