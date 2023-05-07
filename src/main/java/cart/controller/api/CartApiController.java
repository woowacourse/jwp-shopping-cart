package cart.controller.api;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cart.auth.dto.AuthInfo;
import cart.auth.infrastructure.AuthenticationPrincipal;
import cart.domain.cart.CartId;
import cart.domain.product.ProductId;
import cart.service.CartService;
import cart.service.response.CartResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartApiController {

	private final CartService cartService;

	@GetMapping("/items")
	public ResponseEntity<List<CartResponse>> findAllByEmail(@AuthenticationPrincipal AuthInfo authInfo) {
		List<CartResponse> allItems = cartService.findAllByEmail(authInfo.getEmail());
		return ResponseEntity.ok().body(allItems);
	}

	@PostMapping("/{productId}")
	public ResponseEntity<Void> add(@PathVariable long productId, @AuthenticationPrincipal AuthInfo authInfo) {
		final CartId cartId = cartService.insert(authInfo.getEmail(), ProductId.from(productId));
		return ResponseEntity
			.created(URI.create("/cart/" + cartId.getId()))
			.build();
	}

	@DeleteMapping("/{cartId}")
	public ResponseEntity<Void> delete(@PathVariable long cartId, @AuthenticationPrincipal AuthInfo authInfo) {
		cartService.deleteById(authInfo.getEmail(), cartId);
		return ResponseEntity.ok().build();
	}
}
