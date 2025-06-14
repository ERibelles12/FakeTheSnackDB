package de.pecus.api.vo.evaluation;

/**
 * 
 * @author Emilio Ribelles
 *
 * Data entry to recover a list of evaluation for a product
 *
 */
public class FindListEvaluationRequestVO {

	//Product Id
	private Long idProduct;

	public Long getIdProduct() {
		return idProduct;
	}
	public void setIdProduct(Long idProduct) {
		this.idProduct = idProduct;
	}

}
