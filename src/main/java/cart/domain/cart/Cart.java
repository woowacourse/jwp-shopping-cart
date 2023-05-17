package cart.domain.cart;

import cart.domain.member.MemberId;
import cart.domain.product.ProductId;

public class Cart {
	private CartId id;
	private MemberId memberId;
	private ProductId productId;

	public Cart(final CartId id, final MemberId memberId, final ProductId productId) {
		this.id = id;
		this.memberId = memberId;
		this.productId = productId;
	}

	public Cart(final MemberId memberId) {
		this.memberId = memberId;
	}

	public CartId getId() {
		return id;
	}

	public MemberId getMemberId() {
		return memberId;
	}

	public ProductId getProductId() {
		return productId;
	}
}
