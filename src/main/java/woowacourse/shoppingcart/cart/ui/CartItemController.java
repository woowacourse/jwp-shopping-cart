package woowacourse.shoppingcart.cart.ui;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import woowacourse.shoppingcart.auth.support.jwt.AuthenticationPrincipal;
import woowacourse.shoppingcart.cart.application.CartService;
import woowacourse.shoppingcart.cart.application.dto.request.CartDeleteRequest;
import woowacourse.shoppingcart.cart.application.dto.request.CartPutRequest;
import woowacourse.shoppingcart.cart.application.dto.response.CartItemResponse;

@RestController
@RequestMapping("/cart")
public class CartItemController {
    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> getCartItems(@AuthenticationPrincipal final Long customerId) {
        return ResponseEntity.ok().body(cartService.findCartByCustomerId(customerId));
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<CartItemResponse> addCartItem(@AuthenticationPrincipal final Long customerId,
                                                        @PathVariable final Long productId,
                                                        @RequestBody final CartPutRequest cartPutRequest) {
        if (cartService.existsCartItem(customerId, productId)) {
            final CartItemResponse cartItemResponse = cartService.updateCart(customerId, productId, cartPutRequest);
            return ResponseEntity.ok().body(cartItemResponse);
        }
        final CartItemResponse cartItemResponse = cartService.addCart(customerId, productId, cartPutRequest);
        return ResponseEntity.created(URI.create("/cart")).body(cartItemResponse);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCartItem(@AuthenticationPrincipal final Long customerId,
                                               @RequestBody final CartDeleteRequest cartDeleteRequest) {
        cartService.deleteCart(customerId, cartDeleteRequest);
        return ResponseEntity.noContent().build();
    }
}
