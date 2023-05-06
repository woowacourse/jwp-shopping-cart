package cart.controller.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import cart.service.CartService;
import cart.service.response.CartResponse;

@RestController
public class CartApiController {

	private final CartService cartService;

	public CartApiController(final CartService cartService) {
		this.cartService = cartService;
	}

	@GetMapping("/carts/{email}")
	public ResponseEntity<List<CartResponse>> findAllByEmail(@PathVariable(name = "email") String email){
		List<CartResponse> cart = cartService.findAllByEmail(email);
		return ResponseEntity.ok(cart);
	}
}
