package de.pecus.api.vo.funciones;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author Jose_Luis_Garcia
 *
 *	Clase con los parametros de entrada a la invocacion del metodo 
 *  create de la case EventType
 *
 */
public class CreateRolFuncionMenuRequestVO implements Serializable {


	/**
	 * Serial version UID de la clase
	 */
	private static final long serialVersionUID = 8444805927601478412L;
	
	private Long idRolFuncion;
	
	private List<SimpleIdVO> menus;

	/**
	 * @return the idRolFuncion
	 */
	public Long getIdRolFuncion() {
		return idRolFuncion;
	}

	/**
	 * @param idRolFuncion the idRolFuncion to set
	 */
	public void setIdRolFuncion(Long idRolFuncion) {
		this.idRolFuncion = idRolFuncion;
	}

	/**
	 * @return the menus
	 */
	public List<SimpleIdVO> getMenus() {
		return menus;
	}

	/**
	 * @param menus the menus to set
	 */
	public void setMenus(List<SimpleIdVO> menus) {
		this.menus = menus;
	}

}
