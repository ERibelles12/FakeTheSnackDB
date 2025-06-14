package de.pecus.api.vo.evaluation;


import java.util.Date;

/**
 * 
 * @author Emilio Ribelles
 *
 *	Data entry to save an evaluation
 *
 */
public class CreateEvaluationRequestVO {

	//Product
	private Long idProduct;

	//Ingredient with percentaje
	private Long idIngredient;
	// Relation id between product and ingredient
	private Long idRecipe;

	// Ingredient precentaje
	private Long ingredientPercentaje;
	//Evaluation Date.
	private Date evaluationDate;

	public Long getIdProduct() {
		return idProduct;
	}
	public void setIdProduct(Long id) {
		this.idProduct = id;
	}

	public Long getIdIngredient() {
		return idIngredient;
	}
	public void setIdIngredient(Long id) {
		this.idIngredient = id;
	}

	public Long getIdRecipe() {
		return idRecipe;
	}
	public void setIdRecipe(Long id) {
		this.idRecipe = id;
	}

	public Long getIngredientPercentaje() {
		return ingredientPercentaje;
	}
	public void setIngredientPercentaje(Long id) {
		this.ingredientPercentaje = id;
	}

	public Date getEvaluationDate() {
		return evaluationDate;
	}
	public void setEvaluationDate(Date evaluationDate) {
		this.evaluationDate = evaluationDate;
	}

}
