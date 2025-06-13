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
	private Long productId;

	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}

}
