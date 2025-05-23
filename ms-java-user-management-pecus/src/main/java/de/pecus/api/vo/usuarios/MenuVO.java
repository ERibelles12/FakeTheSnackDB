package de.pecus.api.vo.usuarios;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * VO para las paginas del usuario
 */
@JsonInclude(Include.NON_NULL)
public class MenuVO {

	/** Id de la pagina */
	private Long id;

	/** Nombre clave de la pagina */
	private String idNombre;

	/** Nombre de la pagina */
	private String descripcion;

	/** URI de la pagina */
	private String path;
	
	/** Ruta relativa de la imagen del menu  */
	private String imagePath;

	/** Lista de submenus */
	private List<SubMenuVO> listaSubMenu;
	
	/** Orden para el acomodo del menu */
	private Long orden;
	
	/** Pado de la configuracion a partir de la cual se puede mostrar */
	private Double pasoConfig;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the idNombre
	 */
	public String getIdNombre() {
		return idNombre;
	}

	/**
	 * @param idNombre the idNombre to set
	 */
	public void setIdNombre(String idNombre) {
		this.idNombre = idNombre;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	
	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	/**
	 * @return the listaSubMenu
	 */
	public List<SubMenuVO> getListaSubMenu() {
		return listaSubMenu;
	}

	/**
	 * @param listaSubMenu the listaSubMenu to set
	 */
	public void setListaSubMenu(List<SubMenuVO> listaSubMenu) {
		this.listaSubMenu = listaSubMenu;
	}

	public Long getOrden() {
		return orden;
	}

	public void setOrden(Long orden) {
		this.orden = orden;
	}

	public Double getPasoConfig() {
		return pasoConfig;
	}

	public void setPasoConfig(Double pasoConfig) {
		this.pasoConfig = pasoConfig;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idNombre == null) ? 0 : idNombre.hashCode());
		result = prime * result + ((listaSubMenu == null) ? 0 : listaSubMenu.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MenuVO other = (MenuVO) obj;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idNombre == null) {
			if (other.idNombre != null)
				return false;
		} else if (!idNombre.equals(other.idNombre))
			return false;
		if (listaSubMenu == null) {
			if (other.listaSubMenu != null)
				return false;
		} else if (!listaSubMenu.equals(other.listaSubMenu))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}

	
	
}