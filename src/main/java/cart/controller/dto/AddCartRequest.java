package cart.controller.dto;

public class AddCartRequest {

	private Long productId;

	public AddCartRequest() {
	}

	public AddCartRequest(final Long productId) {
		this.productId = productId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(final Long productId) {
		this.productId = productId;
	}
}
