package de.pecus.api.vo.product;

/**
 * 
 * @author Jose_Luis_Garcia
 *
 *	Clase con los parametros de entrada a la invocacion del metodo 
 *  create de la case Recipe
 *
 */
public class DeleteProductIngredientRequestVO {


	// Identificador alfanumerico
	private Long idRecipe;

	public Long getIdRecipe() {return idRecipe;}
	public void setIdRecipe(Long idRecipe) {this.idRecipe = idRecipe;}

}
