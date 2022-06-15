package woowacourse.shoppingcart.ui;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.dto.request.CartItemRequest;
import woowacourse.shoppingcart.dto.response.CartItemResponse;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;

    public CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> showCart(@AuthenticationPrincipal Long memberId) {
        return ResponseEntity.ok()
                .body(cartService.findAll(memberId));
    }

    @PostMapping("/products")
    public ResponseEntity<Void> addCartItem(@AuthenticationPrincipal Long memberId,
                                            @RequestBody CartItemRequest cartItemRequest) {
        cartService.addCartItem(memberId, cartItemRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/products")
    public ResponseEntity<List<CartItemResponse>> updateItemAndShowCart(@AuthenticationPrincipal Long memberId,
                                                                        @RequestBody CartItemRequest updateRequest) {
        cartService.updateCartItemQuantity(memberId, updateRequest);
        return ResponseEntity.ok()
                .body(cartService.findAll(memberId));
    }

    @DeleteMapping("/products")
    public ResponseEntity<List<CartItemResponse>> deleteItemAndShowCart(@AuthenticationPrincipal Long memberId,
                                                                        @RequestParam Long productId) {
        cartService.deleteCartItem(memberId, productId);
        return ResponseEntity.ok()
                .body(cartService.findAll(memberId));
    }
}
