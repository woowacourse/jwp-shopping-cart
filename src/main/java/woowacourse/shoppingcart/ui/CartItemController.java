package woowacourse.shoppingcart.ui;

import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
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

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteCartItem(@AuthenticationPrincipal String email,
                                               @PathVariable final Long productId) {
        cartService.deleteCart(email, productId);
        return ResponseEntity.noContent().build();
    }
}
