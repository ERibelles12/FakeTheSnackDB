package de.pecus.api.vo.evaluation;

import java.util.Date;

/**
 * 
 * @author Emilio Ribelles
 *
 *         Class with the ingredient result
 *
 */
public class ResultItemVO {

	// id
	private Long id;

	private Long recipeId;
	private Long productId;
	private Long ingredientId;
	private String ingredientName;
	private Long evaluationId;
	private Long ingredientMeanPercentage;
	private Long ingredientStdPercentage;
	private Date evaluationDate;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}


	public Long getRecipeId() {
		return recipeId;
	}
	public void setRecipeId(Long recipeId) {
		this.recipeId = recipeId;
	}

	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getIngredientId() {
		return ingredientId;
	}
	public void setIngredientId(Long ingredientId) {
		this.ingredientId = ingredientId;
	}

	public String getIngredientName() {
		return ingredientName;
	}
	public void setIngredientName(String ingredientName) {
		this.ingredientName = ingredientName;
	}

	public Long getEvaluationId() {
		return evaluationId;
	}
	public void setEvaluationId(Long evaluationId) {
		this.evaluationId = evaluationId;
	}

	public Long getIngredientMeanPercentage() { return this.ingredientMeanPercentage;}
	public void setIngredientMeanPercentage(Long ingredientMeanPercentage) {this.ingredientMeanPercentage = ingredientMeanPercentage;}

	public Long getIngredientStdPercentage() { return this.ingredientStdPercentage;}
	public void setIngredientStdPercentage(Long ingredientStdPercentage) {this.ingredientStdPercentage = ingredientStdPercentage;}


	public Date getEvaluationDate() {
		return evaluationDate;
	}
	public void setEvaluationDate(Date evaluationDate) {
		this.evaluationDate = evaluationDate;
	}


}
