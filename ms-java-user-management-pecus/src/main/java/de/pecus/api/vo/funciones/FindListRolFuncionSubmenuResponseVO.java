package de.pecus.api.vo.funciones;

/**
 * 
 * @author Jose_Luis_Garcia
 *
 *         Clase con los parametros de entrada a la invocacion del metodo create
 *         de la case EventType
 *
 */
public class FindListRolFuncionSubmenuResponseVO {

	// id
	private Long id;
	
	private Long idFuncion;
	
	private Long idSubmenu;
	
	private String idNombreSubmenu;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdFuncion() {
		return idFuncion;
	}

	public void setIdFuncion(Long idFuncion) {
		this.idFuncion = idFuncion;
	}

	/**
	 * @return the idSubmenu
	 */
	public Long getIdSubmenu() {
		return idSubmenu;
	}

	/**
	 * @param idSubmenu the idSubmenu to set
	 */
	public void setIdSubmenu(Long idSubmenu) {
		this.idSubmenu = idSubmenu;
	}

	/**
	 * @return the idNombreSubmenu
	 */
	public String getIdNombreSubmenu() {
		return idNombreSubmenu;
	}

	/**
	 * @param idNombreSubmenu the idNombreSubmenu to set
	 */
	public void setIdNombreSubmenu(String idNombreSubmenu) {
		this.idNombreSubmenu = idNombreSubmenu;
	}
	
}
