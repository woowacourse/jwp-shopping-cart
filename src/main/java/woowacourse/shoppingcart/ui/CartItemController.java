package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartItemService;
import woowacourse.shoppingcart.dto.cartitem.CartItemResponse;
import woowacourse.shoppingcart.dto.cartitem.CartItemResponses;
import woowacourse.shoppingcart.dto.cartitem.CartItemSaveRequest;

@RestController
@RequestMapping("/api/cartItems")
public class CartItemController {
    private final CartItemService cartItemService;

    public CartItemController(final CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @GetMapping
    public ResponseEntity<CartItemResponses> getCartItems(@AuthenticationPrincipal final String customerName) {
        return ResponseEntity.ok().body(cartItemService.findCartsByCustomerName(customerName));
    }

    @PostMapping
    public ResponseEntity<CartItemResponse> addCartItem(@RequestBody final CartItemSaveRequest cartItemSaveRequest,
                                                        @AuthenticationPrincipal String customerName) {
        // TODO 반환 status code 체크
        return ResponseEntity.ok(cartItemService.addCart(cartItemSaveRequest, customerName));
    }

    @PatchMapping({"/{cartItemId}"})
    public ResponseEntity<Void> updateCartItemQuantity(@AuthenticationPrincipal String customerName,
                                                       @RequestParam final int quantity,
                                                       @PathVariable final long cartItemId) {
        cartItemService.updateCartItemQuantity(customerName, cartItemId, quantity);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> deleteCartItem(@AuthenticationPrincipal String customerName,
                                               @PathVariable final Long cartItemId) {
        cartItemService.deleteCart(customerName, cartItemId);
        return ResponseEntity.noContent().build();
    }
}
