package de.pecus.api.vo.usuarios;

/******************** FIN SECCION IMPORTS ************************************/

/**
 * 
 * @author jose.ribelles
 * @version 1.0
 * @created 24-abr.-2019 09:31:29 p. m.
 */

public class TipoRolVO {

	private Long id;
	private String idNombre;
	private String descripcion;
	private Boolean global;
	/**
	 * Idioma del componente
	 */
	private IdiomaVO idioma;
	
	/*Cadena a buscar*/
    private String searchQuery;

	public TipoRolVO() {
		// Constructor por defecto
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

	public Boolean getGlobal() {
		return global;
	}

	public void setGlobal(Boolean global) {
		this.global = global;
	}
	
	public IdiomaVO getIdioma() {
		return idioma;
	}

	public void setIdioma(IdiomaVO idioma) {
		this.idioma = idioma;
	}

	public String getSearchQuery() {
		return searchQuery;
	}

	public void setSearchQuery(String searchQuery) {
		this.searchQuery = searchQuery;
	}


}