package de.pecus.api.vo.usuarios;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class SubMenuVO {

	/** Id de la pagina */
	private Long id;

	/** Nombre de la pagina */
	private String idNombre;

	/** Descripcion de la pagina */
	private String descripcion;

	/** URI de la pagina del submenu */
	private String path;
	
	/** Ruta relativa de la imagen del menu  */
	private String imagePath;

	/** Lista de funciones disponibles para el usuario */
	private List<FuncionVO> listaFunciones;
	
	private MenuVO menuVO;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public List<FuncionVO> getListaFunciones() {
		return listaFunciones;
	}

	public void setListaFunciones(List<FuncionVO> listaFunciones) {
		this.listaFunciones = listaFunciones;
	}

	public MenuVO getMenuVO() {
		return menuVO;
	}

	public void setMenuVO(MenuVO menuVO) {
		this.menuVO = menuVO;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idNombre == null) ? 0 : idNombre.hashCode());
		result = prime * result + ((listaFunciones == null) ? 0 : listaFunciones.hashCode());
		result = prime * result + ((menuVO == null) ? 0 : menuVO.hashCode());
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
		SubMenuVO other = (SubMenuVO) obj;
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
		if (listaFunciones == null) {
			if (other.listaFunciones != null)
				return false;
		} else if (!listaFunciones.equals(other.listaFunciones))
			return false;
		if (menuVO == null) {
			if (other.menuVO != null)
				return false;
		} else if (!menuVO.equals(other.menuVO))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}

}