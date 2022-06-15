package woowacourse.shoppingcart.ui.cart;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.ui.cart.dto.request.CartDeleteRequest;
import woowacourse.shoppingcart.ui.cart.dto.request.CartItemRegisterRequest;
import woowacourse.shoppingcart.ui.cart.dto.request.CartItemUpdateRequest;
import woowacourse.shoppingcart.ui.cart.dto.response.CartItemResponse;
import woowacourse.shoppingcart.ui.dto.request.Request;

@RestController
@RequestMapping("/api/customer/carts")
public class CartItemController {

    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@AuthenticationPrincipal final Long customerId,
                                     @Validated(Request.id.class) @RequestBody final CartItemRegisterRequest cartItemRequest) {
        final Long cartId = cartService.addCart(cartItemRequest.getProductId(), customerId);
        final URI responseLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{cartId}")
                .buildAndExpand(cartId)
                .toUri();
        return ResponseEntity.created(responseLocation).build();
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> findCartItems(@AuthenticationPrincipal final Long customerId) {
        final List<Cart> carts = cartService.findCartsByCustomerId(customerId);
        final List<CartItemResponse> cartItemResponses = carts.stream()
                .map(CartItemResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(cartItemResponses);
    }

    @PutMapping
    public ResponseEntity<Void> updateCartItemQuantity(@AuthenticationPrincipal final Long customerId,
                                                       @RequestBody final CartItemUpdateRequest cartItemUpdateRequest) {
        cartService.updateCartItemQuantity(customerId, cartItemUpdateRequest.getProductId(),
                cartItemUpdateRequest.getQuantity());
        return ResponseEntity.noContent().build();

    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@AuthenticationPrincipal final Long customerId,
                                       @RequestBody final CartDeleteRequest cartDeleteRequest) {
        cartService.delete(customerId, cartDeleteRequest.getCartIds());
        return ResponseEntity.noContent().build();
    }
}
