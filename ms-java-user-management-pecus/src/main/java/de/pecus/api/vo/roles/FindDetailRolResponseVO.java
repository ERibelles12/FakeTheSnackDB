package de.pecus.api.vo.roles;


/**
 * 
 * @author Jose_Luis_Garcia
 *
 *	Clase con los parametros de entrada a la invocacion del metodo 
 *  create de la case Rol
 *
 */
public class FindDetailRolResponseVO {


	// id
	private Long id;
	
	// Identificador alfanumerico
	private String idNombre;

	// Descripcion del registro
	private String descripcion;

	// Descripcion del registro
	private String path;
	
	private Long idAplicacion;
	
	private Long idTipoRol;
	
	private String idNombreTipoRol;

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

	public Long getIdAplicacion() {
		return idAplicacion;
	}

	public void setIdAplicacion(Long idAplicacion) {
		this.idAplicacion = idAplicacion;
	}

	public Long getIdTipoRol() {
		return idTipoRol;
	}

	public void setIdTipoRol(Long idTipoRol) {
		this.idTipoRol = idTipoRol;
	}

	public String getIdNombreTipoRol() {
		return idNombreTipoRol;
	}

	public void setIdNombreTipoRol(String idNombreTipoRol) {
		this.idNombreTipoRol = idNombreTipoRol;
	}

	
	
}
