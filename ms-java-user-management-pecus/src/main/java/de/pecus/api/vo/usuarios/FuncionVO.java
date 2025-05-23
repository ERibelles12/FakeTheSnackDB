package de.pecus.api.vo.usuarios;

/******************** FIN SECCION IMPORTS ************************************/

/**
 * 
 * @author jose.ribelles
 * @version 1.0
 * @created 24-abr.-2019 09:31:29 p. m.
 */

public class FuncionVO {

	private Long id;
	private String idNombre;
	private String descripcion;

	public FuncionVO() {
		// Constructor por defecto
	}
	
	public FuncionVO(Long id, String idNombre, String descripcion ) {
		this.id = id;
		this.idNombre = idNombre;
		this.descripcion = descripcion;
	}

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idNombre == null) ? 0 : idNombre.hashCode());
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
		FuncionVO other = (FuncionVO) obj;
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
		return true;
	}

}