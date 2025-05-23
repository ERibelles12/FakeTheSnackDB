package de.pecus.api.vo.funciones;

/**
 * 
 * @author Jose_Luis_Garcia
 *
 *         Clase con los parametros de entrada a la invocacion del metodo create
 *         de la case EventType
 *
 */
public class FindListRolFuncionMenuResponseVO {

	// id
	private Long id;
	
	private Long idRolFuncion;
	
	private Long idMenu;
	
	private String idNombreMenu;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getIdNombreMenu() {
		return idNombreMenu;
	}

	public void setIdNombreMenu(String idNombreMenu) {
		this.idNombreMenu = idNombreMenu;
	}
	
	
}
