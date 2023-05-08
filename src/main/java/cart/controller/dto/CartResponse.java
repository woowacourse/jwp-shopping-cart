package cart.controller.dto;

import cart.service.cart.dto.CartDto;

public class CartResponse {

	private final Long id;
	private final Long userId;
	private final Long productId;
	private final Integer quantity;

	public CartResponse(final CartDto cartDto) {
		this(cartDto.getId(), cartDto.getUserId(), cartDto.getProductId(), cartDto.getQuantity());
	}

	public CartResponse(final Long id, final Long userId, final Long productId, final Integer quantity) {
		this.id = id;
		this.userId = userId;
		this.productId = productId;
		this.quantity = quantity;
	}

	public Long getId() {
		return id;
	}

	public Long getUserId() {
		return userId;
	}

	public Long getProductId() {
		return productId;
	}

	public Integer getQuantity() {
		return quantity;
	}
}
