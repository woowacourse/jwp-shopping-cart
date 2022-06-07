package woowacourse.shoppingcart.ui;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.application.CartService;

import java.util.List;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.ProductIdRequest;

@RestController
@RequestMapping("/users/me/carts")
public class CartItemController {

    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<Object> getCartItems(@AuthenticationPrincipal String email) {
        List<CartItemResponse> cartItemsResponse = cartService.findCartsByCustomerEmail(email);
        return ResponseEntity.ok().body(Map.of("cartList", cartItemsResponse));
    }

    @PostMapping
    public ResponseEntity<Void> addCartItem(@AuthenticationPrincipal String email,
                                            @RequestBody ProductIdRequest productIdRequest) {
        Long productId = Long.valueOf(productIdRequest.getProductId());
        cartService.addCart(productId, email);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable final String customerName,
                                               @PathVariable final Long cartId) {
        cartService.deleteCart(customerName, cartId);
        return ResponseEntity.noContent().build();
    }
}
