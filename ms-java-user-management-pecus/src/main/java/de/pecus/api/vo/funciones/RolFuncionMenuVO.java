/**
 * 
 */
package de.pecus.api.vo.funciones;

import java.io.Serializable;

/**
 * @author 
 *
 */
public class RolFuncionMenuVO implements Serializable {

	/**
	 * Serial version UID de la clase
	 */
	private static final long serialVersionUID = 6423095985621446355L;

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
