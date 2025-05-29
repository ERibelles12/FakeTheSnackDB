package de.pecus.api.entities;

/******************** SECCION IMPORTS ***************************************/

import javax.persistence.*;
import java.io.Serializable;

/**
 * Evaluation
 *
 * For each evaluation we are going to execute, we create a register in the
 * database to keep de evaluations results
 *
 * @author jose.ribelles
 * @version 1.0
 * @created 24-jul.-2019 11:27:46 a. m.
 */
@Entity
@Table(name = "PRODUCT")
public class EvaluationDO extends AuditBase<Long> implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -5382607608047169433L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="PK_ID")
	private Long id;


	@Column(name =  "DD_EVALUATION_DATE" )
	private Date evaluationDate;
	@Column(name =  "DN_SUBSTANCE_PERCENTAJE" )
	private Double substancePercentaje;

	/****************************  RELACION 1..N ******************************/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_BRAND_ID" , referencedColumnName = "PK_ID")
	private BrandDO brand;

	/****************************  RELACION 1..N ******************************/
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_CATEGORY_ID" , referencedColumnName = "PK_ID")
	private CategoryDO category;

	/****************************  RELACION 1..N ******************************/
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_SUBCATEGORY_ID" , referencedColumnName = "PK_ID")
	private SubCategoryDO subCategory;

	/****************************  RELACION 1..N ******************************/
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_PRODUCT_ID" , referencedColumnName = "PK_ID")
	private ProductDO product;

	/****************************  RELACION 1..N ******************************/
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_RECIPE_ID" , referencedColumnName = "PK_ID")
	private RecipeDO recipe;


	public EvaluationDO(){

	}

	public void finalize() throws Throwable {

	}

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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}