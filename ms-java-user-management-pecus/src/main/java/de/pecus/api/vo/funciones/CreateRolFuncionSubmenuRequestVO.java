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
public class CreateRolFuncionSubmenuRequestVO implements Serializable {


	/**
	 * Serial version UID de la clase
	 */
	private static final long serialVersionUID = 8444805927601478412L;

	private Long idFuncion;
	
	private List<SimpleIdVO> submenus;

	/**
	 * @return the idFuncion
	 */
	public Long getIdFuncion() {
		return idFuncion;
	}

	/**
	 * @param idFuncion the idFuncion to set
	 */
	public void setIdFuncion(Long idFuncion) {
		this.idFuncion = idFuncion;
	}

	/**
	 * @return the submenus
	 */
	public List<SimpleIdVO> getSubmenus() {
		return submenus;
	}

	/**
	 * @param submenus the submenus to set
	 */
	public void setSubmenus(List<SimpleIdVO> submenus) {
		this.submenus = submenus;
	}
}
