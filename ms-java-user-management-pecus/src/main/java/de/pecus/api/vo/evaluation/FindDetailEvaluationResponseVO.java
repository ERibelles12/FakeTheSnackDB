package de.pecus.api.vo.evaluation;


import de.pecus.api.vo.category.FindListCategoryResponseVO;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author Emilio Ribelles
 *
 * Data output for a single evaluation
 *
 */
public class FindDetailEvaluationResponseVO {


	// Identificador de registro
	private Long id;

	// Brand Data
	private Long brandId;
	private String brandName;

	// Category Data
	private Long categoryId;
	private String categoryName;

	//SubCategory Data
	private Long subCategoryId;
	private String subCategoryName;

	// Product Data
	private Long productId;
	private String productName;

	// Ingredient Data
	private Long ingredientId;
	private String ingredientName;

	// Recipe Data
	private Long recipeId;

	// Evaluation Date
	private Date evaluationDate;

	// ingredient percentaje
	private Long ingredientPercentaje;

	//List of results
	private List<ResultItemVO> listaResultados;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Long getBrandId() {return brandId;}
	public void setBrandId(Long brandId) {this.brandId = brandId;}
	public String getBrandName() {return brandName;}
	public void setBrandName(String brandName) {this.brandName = brandName;}

	public Long getCategoryId() {return categoryId;}
	public void setCategoryId(Long categoryId) {this.categoryId = categoryId;}
	public String getCategoryName() {return categoryName;}
	public void setCategoryName(String categoryName) {this.categoryName = categoryName;}

	public Long getSubCategoryId() {return subCategoryId;}
	public void setSubCategoryId(Long subCategoryId) {this.subCategoryId = subCategoryId;}
	public String getSubCategoryName() {return subCategoryName;}
	public void setSubCategoryName(String subCategoryName) {this.subCategoryName = subCategoryName;}

	public Long getProductId() {return productId;}
	public void setProductId(Long productId) {this.productId = productId;}
	public String getProductName() {return productName;}
	public void setProductName(String productName) {this.productName = productName;}

	public Long getIngredientId() {return ingredientId;}
	public void setIngredientId(Long ingredientId) {this.ingredientId = ingredientId;}

	public String getIngredientName() {return ingredientName;}
	public void setIngredientName(String ingredientName) {this.ingredientName = ingredientName;}

	public Long getRecipeId() {return recipeId;}
	public void setRecipeId(Long recipeId) {this.recipeId = recipeId;}

	public Date getEvaluationDate() {return evaluationDate;}
	public void setEvaluationDate(Date evaluationDate) {this.evaluationDate = evaluationDate;}

	public Long getIngredientPercentaje() {return ingredientPercentaje;}
	public void setIngredientPercentaje(Long ingredientPercentaje) {this.ingredientPercentaje = ingredientPercentaje;}

	public List<ResultItemVO> getListaResultados() {return listaResultados;}
	public void setListaResultados(List<ResultItemVO> listaResultados) {}

}
