package de.pecus.api.vo.funciones;

/**
 * 
 * @author Jose_Luis_Garcia
 *
 *	Clase con los parametros de entrada a la invocacion del metodo 
 *  findDetail de la case EventType
 *
 */
public class FindListRolFuncionMenuRequestVO {

	private Long idRolFuncion;
	
	private Long idMenu;

	public Long getIdRolFuncion() {
		return idRolFuncion;
	}

	public void setIdRolFuncion(Long idRolFuncion) {
		this.idRolFuncion = idRolFuncion;
	}

	public Long getIdMenu() {
		return idMenu;
	}

	public void setIdMenu(Long idMenu) {
		this.idMenu = idMenu;
	}

	
}
