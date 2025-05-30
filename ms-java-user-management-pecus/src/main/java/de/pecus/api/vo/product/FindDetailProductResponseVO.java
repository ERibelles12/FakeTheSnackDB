package de.pecus.api.vo.product;


/**
 * 
 * @author Jose_Luis_Garcia
 *
 *	Clase con los parametros de entrada a la invocacion del metodo 
 *  create de la case EventType
 *
 */
public class FindDetailProductResponseVO {

	// Identificador de registro
	private Long id;

	// Identificador alfanumerico
	private String name;

	// Descripcion del registro
	private String descripcion;

	// Brand
	private Integer idBrand;
	private String nameBrand;

	//Category
	private Integer idCategory;
	private String nameCategory;

	//SubCategory
	private Integer idSubCategory;
	private String nameSubCategory;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getDescripcion() {return descripcion;}
	public void setDescripcion(String descripcion) {this.descripcion = descripcion;}

	public Integer getIdBrand() {return this.idBrand;}
	public void setIdBrand(Integer idBrand) {this.idBrand = idBrand;}

	public Integer getIdCategory() {return this.idCategory;}
	public void setIdCategory(Integer idCategory) {this.idCategory = idCategory;}

	public Integer getIdSubCategory() {return this.idSubCategory;}
	public void setIdSubCategory(Integer idSubCategory) {this.idSubCategory = idSubCategory;}

	public String getNameBrand() {return this.nameBrand;}
	public void setNameBrand(String nameBrand) {this.nameBrand = nameBrand;}

	public String getNameCategory() {return this.nameCategory;}
	public void setNameCategory(String nameCategory) {this.nameCategory = nameCategory;}

	public String getNameSubCategory() {return this.nameSubCategory;}
	public void setNameSubCategory(String nameSubCategory) {this.nameSubCategory = nameSubCategory;}

}
