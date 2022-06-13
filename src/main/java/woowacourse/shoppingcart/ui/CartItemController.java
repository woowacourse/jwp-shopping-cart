package woowacourse.shoppingcart.ui;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.dto.CartIdRequest;
import woowacourse.shoppingcart.dto.CartProductInfoRequest;
import woowacourse.shoppingcart.dto.CartProductInfoResponse;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.dto.ProductIdRequest;

@RestController
@RequestMapping("/auth/customer/cartItems")
public class CartItemController {
    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<List<CartProductInfoResponse>> addCartItem(
            @AuthenticationPrincipal final TokenRequest tokenRequest,
            @RequestBody final List<ProductIdRequest> productIdRequests) {
        return ResponseEntity.ok().body(cartService.addCarts(productIdRequests, tokenRequest.getCustomerId()));
    }

    @GetMapping
    public ResponseEntity<List<CartResponse>> getCartItems(@AuthenticationPrincipal final TokenRequest tokenRequest) {
        return ResponseEntity.ok().body(cartService.findCartsByCustomerId(tokenRequest.getCustomerId()));
    }

    @PatchMapping
    public ResponseEntity<CartProductInfoResponse> updateCartItem(
            @AuthenticationPrincipal final TokenRequest tokenRequest,
            @RequestBody final CartProductInfoRequest cartProductInfoRequests) {
        return ResponseEntity.ok().body(cartService.patchCart(cartProductInfoRequests, tokenRequest.getCustomerId()));
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteCartItem(@AuthenticationPrincipal final TokenRequest tokenRequest,
                                               @RequestBody final List<CartIdRequest> cartIdRequests) {
        cartService.deleteCarts(tokenRequest.getCustomerId(), cartIdRequests);
        return ResponseEntity.noContent().build();
    }
}
