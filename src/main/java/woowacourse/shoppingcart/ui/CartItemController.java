package woowacourse.shoppingcart.ui;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartItemService;
import woowacourse.shoppingcart.dto.CartItemIdRequest;
import woowacourse.shoppingcart.dto.CartItemQuantityRequest;
import woowacourse.shoppingcart.dto.CartItemQuantityResponse;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.ProductIdRequest;
import woowacourse.shoppingcart.dto.TokenRequest;

@RestController
public class CartItemController {

    private final CartItemService cartItemService;

    public CartItemController(final CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @GetMapping("/auth/customer/cartItems")
    public ResponseEntity<List<CartItemResponse>> findCartItems(final @AuthenticationPrincipal TokenRequest request) {
        List<CartItemResponse> responses = cartItemService.findCartItemsByCustomerId(request);
        return ResponseEntity.ok().body(responses);
    }

    @PostMapping("/auth/customer/cartItems")
    public ResponseEntity<List<CartItemQuantityResponse>> addCartItems(
            final @AuthenticationPrincipal TokenRequest tokenRequest,
            final @RequestBody List<ProductIdRequest> productIdRequests
    ) {
        List<CartItemQuantityResponse> responses = cartItemService.addCartItems(tokenRequest, productIdRequests);
        return ResponseEntity.ok().body(responses);
    }

    @PatchMapping("/auth/customer/cartItems")
    public ResponseEntity<CartItemQuantityResponse> updateCartItem(
            final @AuthenticationPrincipal TokenRequest tokenRequest,
            final @RequestBody CartItemQuantityRequest cartItemQuantityRequest
    ) {
        CartItemQuantityResponse response = cartItemService.updateCartItem(tokenRequest, cartItemQuantityRequest);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/auth/customer/cartItems")
    public ResponseEntity<Void> delete(final @RequestBody List<CartItemIdRequest> cartItemIdRequests) {
        cartItemService.delete(cartItemIdRequests);
        return ResponseEntity.noContent().build();
    }
}
