package de.pecus.api.vo.menu;

/**
 * 
 * @author Jose_Luis_Garcia
 *
 * Clase con los parametros de entrada a la invocacion del metodo 
 * findDetail de la case EventType
 *
 */
public class FindListSubMenuRequestVO {

	private Long idMenu;
	
	private String idNombre;

	public String getIdNombre() {
		return idNombre;
	}

	public void setIdNombre(String idNombre) {
		this.idNombre = idNombre;
	}

	public Long getIdMenu() {
		return idMenu;
	}

	public void setIdMenu(Long idMenu) {
		this.idMenu = idMenu;
	}
}
