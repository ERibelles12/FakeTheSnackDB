package de.pecus.api.vo.evaluation;

import java.util.Date;

/**
 * 
 * @author Jose_Luis_Garcia
 *
 *         Clase con los parametros de entrada a la invocacion del metodo create
 *         de la case EventType
 *
 */
public class FindListEvaluationResponseVO {

	// id
	private Long id;
	private Long idBrand;
	private String brandName;
	private Long idCategory;
	private String categoryName;
	private Long idSubCategory;
	private String subCategoryName;

	private Long idProduct;
	private String productName;
	private Long idIngredient;
	private String ingredientName;
	private Long idRecipe;
	private Date evaluationDate;
	private Long ingredientPercentaje;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdBrand() {return idBrand;}
	public void setIdBrand(Long idBrand) {this.idBrand = idBrand;}
	public String getBrandName() {return brandName;}
	public void setBrandName(String brandName) {this.brandName = brandName;}
	public Long getIdCategory() {return idCategory;}
	public void setIdCategory(Long idCategory) {this.idCategory = idCategory;}
	public String getCategoryName() {return categoryName;}
	public void setCategoryName(String categoryName) {this.categoryName = categoryName;}
	public Long getIdSubCategory() {return idSubCategory;}
	public void setIdSubCategory(Long idSubCategory) {this.idSubCategory = idSubCategory;}
	public String getSubCategoryName() {return subCategoryName;}
	public void setSubCategoryName(String subCategoryName) {this.subCategoryName = subCategoryName;}
	public Long getIdProduct() {return idProduct;}
	public void setIdProduct(Long idProduct) {this.idProduct = idProduct;}
	public String getProductName() {return productName;}
	public void setProductName(String productName) {this.productName = productName;}
	public Long getIdIngredient() {return idIngredient;}
	public void setIdIngredient(Long idIngredient) {this.idIngredient = idIngredient;}
	public String getIngredientName() {return ingredientName;}
	public void setIngredientName(String ingredientName) {this.ingredientName = ingredientName;}
	public Long getIdRecipe() {return idRecipe;}
	public void setIdRecipe(Long idRecipe) {this.idRecipe = idRecipe;}
	public Date getEvaluationDate() {return evaluationDate;}
	public void setEvaluationDate(Date evaluationDate) {this.evaluationDate = evaluationDate;}
	public Long getIngredientPercentaje() {return ingredientPercentaje;}
	public void setIngredientPercentaje(Long ingredientPercentaje) {this.ingredientPercentaje = ingredientPercentaje;}


}
