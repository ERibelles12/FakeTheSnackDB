package de.pecus.api.vo.funciones;

/**
 * 
 * @author Jose_Luis_Garcia
 *
 *	Clase con los parametros de entrada a la invocacion del metodo 
 *  findDetail de la case EventType
 *
 */
public class FindListRolFuncionRequestVO {

	private Long idAplicacion;

	private Long idRol;
	
	public Long getIdAplicacion() {
		return idAplicacion;
	}

	public void setIdAplicacion(Long idAplicacion) {
		this.idAplicacion = idAplicacion;
	}

	public Long getIdRol() {
		return idRol;
	}

	public void setIdRol(Long idRol) {
		this.idRol = idRol;
	}

	
	
}
