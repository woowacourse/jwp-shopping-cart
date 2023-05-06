package cart.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cart.service.response.CartResponse;

@Service
public class CartServiceImpl implements CartService {
	@Override
	public List<CartResponse> findAllByEmail(final String email) {
		return List.of(new CartResponse(1L, "https://img.freepik.com/premium-photo/red-apples-isolated-on-white-background-ripe-fresh-apples-clipping-path-apple-with-leaf_299651-595.jpg", "apple", 1000));
	}
}
