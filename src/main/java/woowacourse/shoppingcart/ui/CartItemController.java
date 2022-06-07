package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.dto.request.CustomerIdentificationRequest;
import woowacourse.shoppingcart.application.dto.request.ProductIdRequest;
import woowacourse.shoppingcart.application.dto.response.CartItemResponse;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.application.CartService;

import java.util.List;

@RestController
public class CartItemController {
    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/api/customers/{customerName}/carts")
    public ResponseEntity<List<Cart>> getCartItems(@PathVariable final String customerName) {
        return ResponseEntity.ok().body(cartService.findCartsByCustomerName(customerName));
    }

    @PostMapping("/auth/customer/cartItems")
    public ResponseEntity<List<CartItemResponse>> addCartItems(@AuthenticationPrincipal CustomerIdentificationRequest customerIdentificationRequest,
                                            @RequestBody List<ProductIdRequest> productIdRequests) {
        return ResponseEntity.ok().body(cartService.addCartItems(customerIdentificationRequest, productIdRequests));
    }

    @DeleteMapping("/api/customers/{customerName}/carts/{cartId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable final String customerName,
                                         @PathVariable final Long cartId) {
        cartService.deleteCart(customerName, cartId);
        return ResponseEntity.noContent().build();
    }
}
