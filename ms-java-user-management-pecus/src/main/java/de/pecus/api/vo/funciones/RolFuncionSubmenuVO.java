/**
 * 
 */
package de.pecus.api.vo.funciones;

import java.io.Serializable;

/**
 * @author 
 *
 */
public class RolFuncionSubmenuVO implements Serializable {

	/**
	 * Serial version UID de la clase
	 */
	private static final long serialVersionUID = 6423095985621446355L;

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
