package woowacourse.shoppingcart.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.dto.response.CartItemResponse;
import woowacourse.shoppingcart.dto.request.LoginCustomer;
import woowacourse.shoppingcart.dto.request.ProductIdRequest;
import woowacourse.shoppingcart.dto.request.QuantityRequest;
import woowacourse.shoppingcart.dto.request.Request;
import woowacourse.shoppingcart.application.CartService;

import java.util.List;
import woowacourse.shoppingcart.dto.response.CartResponse;

@RestController
@RequestMapping("/customers/carts")
public class CartItemController {
    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> getCartItems(@AuthenticationPrincipal LoginCustomer loginCustomer) {
        final CartResponse cartResponse = cartService.findCartByCustomerId(loginCustomer.getId());
        return ResponseEntity.ok().body(cartResponse.getCartItemResponses());
    }

    @PostMapping
    public ResponseEntity<CartItemResponse> addCartItem(@Validated(Request.id.class) @RequestBody final ProductIdRequest productIdRequest,
                                      @AuthenticationPrincipal LoginCustomer loginCustomer) {
        final CartItemResponse cartItemResponse = cartService.addCart(productIdRequest.getProductId(), loginCustomer.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemResponse);
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> deleteCartItem(@AuthenticationPrincipal LoginCustomer loginCustomer,
                                         @PathVariable final Long cartItemId) {
        cartService.deleteCartItem(loginCustomer.getId(), cartItemId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{cartItemId}")
    public ResponseEntity<CartItemResponse> updateCartItemQuantity(
        @AuthenticationPrincipal LoginCustomer loginCustomer,
        @PathVariable final Long cartItemId, @RequestBody QuantityRequest quantityRequest) {
        CartItemResponse cartItemResponse = cartService.updateCartItemQuantity(loginCustomer.getId(), cartItemId, quantityRequest.getQuantity());
        return ResponseEntity.ok().body(cartItemResponse);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCart(@AuthenticationPrincipal LoginCustomer loginCustomer) {
        cartService.deleteCart(loginCustomer.getId());
        return ResponseEntity.noContent().build();
    }

}
