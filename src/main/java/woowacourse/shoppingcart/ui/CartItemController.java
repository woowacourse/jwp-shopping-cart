package woowacourse.shoppingcart.ui;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.CartItemRequest;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.dto.Request;

@RestController
@RequestMapping("/api/customers/{customerId}/carts")
public class CartItemController {

    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartResponse>> getCartItems(@PathVariable final long customerId) {
        List<Cart> carts = cartService.findCartsByCustomerId(customerId);
        List<CartResponse> cartResponses = carts.stream()
            .map(CartResponse::new)
            .collect(Collectors.toList());
        return ResponseEntity.ok().body(cartResponses);
    }

    @PostMapping
    public ResponseEntity<Void> addCartItem(@Validated(Request.id.class) @RequestBody final CartItemRequest request,
        @PathVariable final long customerId) {
        final Long cartId = cartService.addCart(request.getProductId(), customerId, request.getCount());
        final URI responseLocation = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{cartId}")
            .buildAndExpand(cartId)
            .toUri();
        return ResponseEntity.created(responseLocation).build();
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable final long customerId,
        @PathVariable final Long cartId) {
        cartService.deleteCart(customerId, cartId);
        return ResponseEntity.noContent().build();
    }
}
