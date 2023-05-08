package cart.domain.cart;

import java.time.LocalDateTime;

public class Cart {
	private Long id;
	private Long userId;
	private Long productId;
	private Integer quantity;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public Cart(final Long userId, final Long productId) {
		this(null, userId, productId, null, null, null);
	}

	public Cart(final Long cartId, final Long userId, final Long productId, final Integer quantity) {
		this(cartId, userId, productId, quantity, null, null);
	}

	public Cart(final Long id, final Long userId, final Long productId, final Integer quantity,
		final LocalDateTime createdAt, final LocalDateTime updatedAt) {
		this.id = id;
		this.userId = userId;
		this.productId = productId;
		this.quantity = quantity;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public void setUserId(final Long userId) {
		this.userId = userId;
	}

	public void setProductId(final Long productId) {
		this.productId = productId;
	}

	public void setQuantity(final Integer quantity) {
		this.quantity = quantity;
	}

	public void setCreatedAt(final LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public void setUpdatedAt(final LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
}
