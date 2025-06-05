package de.pecus.api.vo.product;


import java.util.Date;

/**
 * 
 * @author Jose_Luis_Garcia
 *
 *	Clase con los parametros de entrada a la invocacion del metodo 
 *  create de la case Recipe
 *
 */
public class AssociateProductSubstanceRequestVO {


	// Identificador alfanumerico
	private Long idProduct;

	// Descripcion del registro
	private Long idSubstance;

	// Fecha de registro
	private Date fechaRegistro;

	public Long getIdProduct() {return idProduct;}
	public void setIdProduct(Long idProduct) {this.idProduct = idProduct;}

	public Long getIdSubstance() {return idSubstance;}
	public void setIdSubstance(Long idSubstance) {this.idSubstance = idSubstance;}

	public Date getFechaRegistro() {return fechaRegistro;}
	public void setFechaRegistro(Date fechaRegistro) {this.fechaRegistro = fechaRegistro;}

}
