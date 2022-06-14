package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.dto.request.DeleteProductIds;
import woowacourse.shoppingcart.dto.request.UpdateProductQuantityRequest;
import woowacourse.shoppingcart.dto.response.CartItemsResponse;

@RestController
@RequestMapping("/cart")
public class CartItemController {
    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<CartItemsResponse> getCartItems(@AuthenticationPrincipal Long customerId) {
        CartItemsResponse response = new CartItemsResponse(cartService.findCartItems(customerId));
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("{productId}")
    public ResponseEntity<Void> addCartItem(@AuthenticationPrincipal Long customerId,
                                      @PathVariable final Long productId) {
        cartService.addCart(customerId, productId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{productId}/quantity")
    public ResponseEntity<Void> updateProductQuantity(@AuthenticationPrincipal Long customerId,
                                                      @PathVariable final Long productId,
                                                      @RequestBody UpdateProductQuantityRequest updateProductQuantityRequest) {
        cartService.updateQuantity(customerId, productId, updateProductQuantityRequest.getQuantity());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/products")
    public ResponseEntity<Void> deleteCartItem(@AuthenticationPrincipal Long customerId,
                                         @RequestBody DeleteProductIds deleteProductIds) {
        cartService.deleteCart(customerId, deleteProductIds.getProductIds());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllCartItem(@AuthenticationPrincipal Long customerId) {
        cartService.emptyCart(customerId);
        return ResponseEntity.noContent().build();
    }
}
