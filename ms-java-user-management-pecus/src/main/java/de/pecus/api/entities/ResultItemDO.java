package de.pecus.api.entities;

/******************** SECCION IMPORTS ***************************************/

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Catalog Recipe
 * 
 * Resultt deetail entity
 * @author jose.ribelles
 * @version 1.0
 * @created 24-jul.-2025 11:27:46 a. m.
 */
@Entity
@Table(name = "ResultT_ITEM")
public class ResultItemDO extends AuditBase<Long> implements Serializable {

	private static final long serialVersionUID = -5382607608047169433L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="PK_ID")
	private Long id;

	/****************************  RELACION 1..N ******************************/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_EVALUATION_ID" , referencedColumnName = "PK_ID")
	private EvaluationDO evaluation;

	/****************************  RELACION 1..N ******************************/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_PRODUCT_ID" , referencedColumnName = "PK_ID")
	private ProductDO product;

	/****************************  RELACION 1..N ******************************/
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_INGREDIENT_ID" , referencedColumnName = "PK_ID")
	private IngredientDO ingredient;

	/****************************  RELACION 1..N ******************************/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_RECIPE_ID" , referencedColumnName = "PK_ID")
	private RecipeDO recipe;

	@Column(name="DN_INGREDIENT_MEAN_PERCENTAGE")
	private Long ingredientMeanPercentage;

	@Column(name="DN_INGREDIENT_STD_PERCENTAGE")
	private Long ingredientStdPercentage;

	@Column(name="DD_EVALUATION_DATE")
	private Date evaluationDate;

	public ResultItemDO(){

	}

	public void finalize() throws Throwable {

	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public EvaluationDO getEvaluation() {
		return evaluation;
	}
	public void setEvaluation(EvaluationDO id) {
		this.evaluation = id;
	}

	public ProductDO getProduct() {
		return product;
	}
	public void setProduct(ProductDO id) {
		this.product = id;
	}

	public IngredientDO getIngredient() {
		return ingredient;
	}
	public void setIngredient(IngredientDO id) {
		this.ingredient = id;
	}

	public RecipeDO getRecipe() {
		return recipe;
	}
	public void setIngredient(RecipeDO id) {
		this.recipe = id;
	}

	public Long getIngredientMeanPercentage() { return this.ingredientMeanPercentage;}
	public void setIngredientMeanPercentage(Long ingredientMeanPercentage) {this.ingredientMeanPercentage = ingredientMeanPercentage;}

	public Long getIngredientStdPercentage() { return this.ingredientStdPercentage;}
	public void setIngredientStdPercentage(Long ingredientStdPercentage) {this.ingredientStdPercentage = ingredientStdPercentage;}

	public Date getEvaluationDate() { return this.evaluationDate;}
	public void setEvaluationDate(Date evaluationDate) {this.evaluationDate = evaluationDate;}

}