package cart.service;

import java.util.List;

import cart.service.response.CartResponse;

public interface CartService {
	List<CartResponse> findAllByEmail(String email);
}
