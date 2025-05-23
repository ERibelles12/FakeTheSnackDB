package de.pecus.api.vo.roles;

/**
 * 
 * @author Jose_Luis_Garcia
 *
 *	Clase con los parametros de entrada a la invocacion del metodo 
 *  findDetail de la case EventType
 *
 */
public class FindListRolRequestVO {

	private Long idAplicacion;

	private String idNombre;
	
	private Long idUsuario;

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

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
}
