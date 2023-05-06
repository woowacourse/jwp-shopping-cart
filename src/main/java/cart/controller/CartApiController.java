package cart.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cart.config.auth.LoginUser;
import cart.config.auth.dto.AuthUser;
import cart.controller.dto.AddCartRequest;
import cart.controller.dto.CartResponse;
import cart.dao.cart.dto.CartProductDto;
import cart.service.cart.CartService;
import cart.service.cart.dto.CartDto;

@RestController
@RequestMapping("/carts/products")
public class CartApiController {

	private final CartService cartService;

	public CartApiController(final CartService cartService) {
		this.cartService = cartService;
	}

	@GetMapping
	public ResponseEntity<List<CartProductDto>> getCart(@LoginUser AuthUser authUser) {
		final String email = authUser.getEmail();
		final List<CartProductDto> cartProductDtos = cartService.findByEmail(email);

		return ResponseEntity.ok(cartProductDtos);
	}

	@PostMapping
	public ResponseEntity<CartResponse> addProduct(@RequestBody final AddCartRequest addCartRequest, @LoginUser AuthUser authUser) {
		final Long userId = authUser.getId();
		final Long productId = addCartRequest.getProductId();
		System.out.println(productId);
		final CartDto cartDto = cartService.addProduct(userId, productId);
		final CartResponse cartResponse = new CartResponse(cartDto);

		return ResponseEntity.created(URI.create("/cart")).body(cartResponse);
	}

	@PatchMapping("/{productId}")
	public ResponseEntity<CartResponse> updateQuantity(@PathVariable final Long productId,
		@LoginUser AuthUser authUser) {
		final Long userId = authUser.getId();
		final CartDto cartDto = cartService.updateQuantity(userId, productId);
		final CartResponse cartResponse = new CartResponse(cartDto);

		return ResponseEntity.ok(cartResponse);
	}

	@DeleteMapping("/{productId}")
	public ResponseEntity<Void> deleteProduct(@PathVariable final Long productId, @LoginUser AuthUser authUser) {
		final Long userId = authUser.getId();
		cartService.deleteProduct(userId, productId);

		return ResponseEntity.noContent()
			.build();
	}
}
