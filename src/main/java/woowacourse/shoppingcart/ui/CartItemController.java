package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.dto.request.CartItemIdRequest;
import woowacourse.shoppingcart.application.dto.request.CartItemRequest;
import woowacourse.shoppingcart.application.dto.request.CustomerIdentificationRequest;
import woowacourse.shoppingcart.application.dto.request.ProductIdRequest;
import woowacourse.shoppingcart.application.dto.response.CartItemResponse;
import woowacourse.shoppingcart.application.dto.response.CartResponse;
import woowacourse.shoppingcart.application.CartService;

import java.util.List;

@RestController
public class CartItemController {
    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/auth/customer/cartItems")
    public ResponseEntity<List<CartResponse>> getCartItems(@AuthenticationPrincipal CustomerIdentificationRequest customerIdentificationRequest) {
        return ResponseEntity.ok().body(cartService.findCarts(customerIdentificationRequest));
    }

    @PostMapping("/auth/customer/cartItems")
    public ResponseEntity<List<CartItemResponse>> addCartItems(@AuthenticationPrincipal CustomerIdentificationRequest customerIdentificationRequest,
                                            @RequestBody List<ProductIdRequest> productIdRequests) {
        return ResponseEntity.ok().body(cartService.addCartItems(customerIdentificationRequest, productIdRequests));
    }

    @PatchMapping("/auth/customer/cartItems")
    public ResponseEntity<CartItemResponse> updateQuantity(@AuthenticationPrincipal CustomerIdentificationRequest customerIdentificationRequest,
                                                           @RequestBody CartItemRequest cartItemRequest) {
        return ResponseEntity.ok().body(cartService.updateQuantity(customerIdentificationRequest, cartItemRequest));
    }

    @DeleteMapping("/auth/customer/cartItems")
    public ResponseEntity<Void> deleteCartItems(@AuthenticationPrincipal CustomerIdentificationRequest customerIdentificationRequest,
                                                @RequestBody List<CartItemIdRequest> cartItemIdRequests) {
        cartService.deleteCarts(cartItemIdRequests);
        return ResponseEntity.noContent().build();
    }
}
