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
	private Long productId;

	//Ingredient with percentaje
	private Long ingredientId;
	// Relation id between product and ingredient
	private Long recipeId;

	// Ingredient precentaje
	private Long ingredientPercentaje;
	//Evaluation Date.
	private Date evaluationDate;

	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long id) {
		this.productId = id;
	}

	public Long getIngredientId() {
		return ingredientId;
	}
	public void setIngredientId(Long id) {
		this.ingredientId = id;
	}

	public Long getRecipeId() {
		return recipeId;
	}
	public void setRecipeId(Long id) {
		this.recipeId = id;
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
