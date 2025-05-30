package de.pecus.api.vo.product;


/**
 * 
 * @author Jose_Luis_Garcia
 *
 *	Clase con los parametros de entrada a la invocacion del metodo 
 *  create de la case EventType
 *
 */
public class CreateProductRequestVO {


	// Identificador alfanumerico
	private String name;

	// Descripcion del registro
	private String descripcion;

	// Product Id Brand
	private Integer idBrand;

	// Product Id Category
	private Integer idCategory;

	// Product Id Subcategory
	private Integer idSubCategory;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getIdBrand() { return idBrand;}
	public void setIdBrand(Integer idBrand) { this.idBrand = idBrand;}

	public Integer getIdCategory() {return idCategory;}
	public void setIdCategory() {this.idCategory = idCategory;}

	public Integer getIdSubCategory() {return idSubCategory;}
	public void setIdSubCategory() {this.idSubCategory = idSubCategory;}

}
