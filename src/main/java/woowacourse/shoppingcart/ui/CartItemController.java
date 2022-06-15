package woowacourse.shoppingcart.ui;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import woowacourse.auth.domain.Customer;
import woowacourse.auth.support.Login;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.dto.ProductIdsRequest;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.QuantityRequest;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartItemController {

	private final CartService cartService;

	@PutMapping("/products/{productId}")
	public ResponseEntity<CartItemResponse> addCartItem(@Login Customer customer,
		@PathVariable Long productId, @RequestBody QuantityRequest quantityRequest) {
		if (cartService.existByCustomerAndProduct(customer.getId(), productId)) {
			CartItem cartItem = cartService.setItem(customer.getId(), productId, quantityRequest.getQuantity());
			return ResponseEntity.ok(CartItemResponse.from(cartItem));
		}
		CartItem cartItem = cartService.setItem(customer.getId(), productId, quantityRequest.getQuantity());
		return ResponseEntity.created(makeUri(cartItem.getId()))
			.body(CartItemResponse.from(cartItem));
	}

	private URI makeUri(Long id) {
		return ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{cartId}")
			.buildAndExpand(id)
			.toUri();
	}

	@GetMapping
	public ResponseEntity<List<CartItemResponse>> getCartItems(@Login Customer customer) {
		List<CartItem> items = cartService.findItemsByCustomer(customer.getId());
		return ResponseEntity.ok().body(items.stream()
			.map(CartItemResponse::from)
			.collect(Collectors.toList())
		);
	}

	@DeleteMapping
	public ResponseEntity<Void> deleteCartItem(@Login Customer customer, @RequestBody ProductIdsRequest request) {
		cartService.deleteItems(customer.getId(), request.getProductIds());
		return ResponseEntity.noContent().build();
	}
}
