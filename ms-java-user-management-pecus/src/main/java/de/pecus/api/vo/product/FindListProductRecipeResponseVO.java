package de.pecus.api.vo.product;

import java.util.Date;

/**
 * 
 * @author Jose_Luis_Garcia
 *
 *         Clase con los parametros de entrada a la invocacion del metodo create
 *         de la case EventType
 *
 */
public class FindListProductRecipeResponseVO {

	// id
	private Long id;

	// Product
	private Long idProduct;
	private String nameProduct;

	// Substance
	private Long idSubstance;
	private String nameSubstance;

	//SubCategory
	private Date fechaRegistro;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {this.id = id;}

	public Long getIdProduct() {
		return idProduct;
	}
	public void setIdProduct(Long idProduct) {
		this.idProduct = idProduct;
	}

	public String getNameProduct() {
		return nameProduct;
	}
	public void setNameProduct(String nameProduct) {
		this.nameProduct = nameProduct;
	}

	public Long getIdSubstance() {
		return idSubstance;
	}
	public void setIdSubstance(Long idSubstance) {
		this.idSubstance = idSubstance;
	}

	public String getNameSubstance() {
		return nameSubstance;
	}
	public void setNameSubstance(String nameSubstance) {
		this.nameSubstance = nameSubstance;
	}

}
