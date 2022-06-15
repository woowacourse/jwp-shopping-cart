package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.cart.CartItemAddRequest;
import woowacourse.shoppingcart.dto.cart.CartItemResponse;
import woowacourse.shoppingcart.dto.cart.CartItemUpdateRequest;
import woowacourse.shoppingcart.dto.cart.CartItemsResponse;

import java.util.List;

@RestController
@RequestMapping("/users/me/carts")
public class CartItemController {
    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<CartItemsResponse> getCartItems(@AuthenticationPrincipal Customer customer) {
        List<CartItem> cartItems = cartService.findCartsByCustomer(customer);
        return ResponseEntity.ok(CartItemsResponse.from(cartItems));
    }

    @PostMapping
    public ResponseEntity<Void> addCartItem(@RequestBody CartItemAddRequest cartItemAddRequest, @AuthenticationPrincipal Customer customer) {
        cartService.addCart(cartItemAddRequest.getProductId(), customer);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable final Long productId, @AuthenticationPrincipal Customer customer) {
        cartService.deleteCart(customer, productId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{productId}")
    public ResponseEntity<CartItemResponse> updateCartItemQuantity(@PathVariable Long productId,
                                                                   @RequestBody CartItemUpdateRequest request,
                                                                   @AuthenticationPrincipal Customer customer) {
        CartItem cartItem = cartService.updateQuantity(request, customer, productId);
        return ResponseEntity.ok(CartItemResponse.from(cartItem));
    }
}
