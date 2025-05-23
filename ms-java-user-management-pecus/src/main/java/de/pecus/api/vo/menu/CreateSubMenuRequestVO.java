package de.pecus.api.vo.menu;


/**
 * 
 * @author Jose_Luis_Garcia
 *
 *	Clase con los parametros de entrada a la invocacion del metodo 
 *  create de la case EventType
 *
 */
public class CreateSubMenuRequestVO {


	private Long idMenu;
	
	private String idNombre;
	
	private String descripcion;
	
	private String path;
	
	private String imagePath;

	public Long getIdMenu() {
		return idMenu;
	}

	public void setIdMenu(Long idMenu) {
		this.idMenu = idMenu;
	}

	public String getIdNombre() {
		return idNombre;
	}

	public void setIdNombre(String idNombre) {
		this.idNombre = idNombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	
	
}
