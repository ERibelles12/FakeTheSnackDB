package de.pecus.api.vo.funciones;

/**
 * 
 * @author Jose_Luis_Garcia
 *
 *	Clase con los parametros de entrada a la invocacion del metodo 
 *  findDetail de la case EventType
 *
 */
public class FindListRolFuncionSubmenuRequestVO {

	private Long idFuncion;
	
	private Long idSubmenu;

	public Long getIdFuncion() {
		return idFuncion;
	}

	public void setIdFuncion(Long idFuncion) {
		this.idFuncion = idFuncion;
	}

	public Long getIdSubmenu() {
		return idSubmenu;
	}

	public void setIdSubmenu(Long idSubmenu) {
		this.idSubmenu = idSubmenu;
	}

	
}
