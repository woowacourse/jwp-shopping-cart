package cart.service;

import java.util.List;

import cart.domain.cart.CartId;
import cart.domain.member.MemberId;
import cart.domain.product.ProductId;
import cart.service.response.CartResponse;

public interface CartService {
	CartId insert(MemberId memberId, ProductId productId);

	List<CartResponse> findAllByEmail(String email);
}
