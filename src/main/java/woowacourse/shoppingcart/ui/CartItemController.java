package woowacourse.shoppingcart.ui;

import java.util.List;

import javax.validation.Valid;

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

import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.domain.cart.Cart;
import woowacourse.shoppingcart.dto.cart.CartItemRequest;
import woowacourse.shoppingcart.dto.cart.CartItemResponse;
import woowacourse.shoppingcart.dto.cart.CartItemUpdateRequest;
import woowacourse.shoppingcart.dto.cart.CartResponse;

@RestController
@RequestMapping("/api/customers/{customerId}/carts")
public class CartItemController {

    private final CartService cartService;

    public CartItemController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> getCartItems(@PathVariable long customerId) {
        Cart cart = cartService.findCartByCustomerId(customerId);
        CartResponse cartResponse = CartResponse.from(cart);
        return ResponseEntity.ok().body(cartResponse.getValue());
    }

    @PostMapping
    public ResponseEntity<Void> addCartItem(@Valid @RequestBody CartItemRequest request,
        @PathVariable final long customerId) {
        cartService.addCartItem(request.getProductId(), customerId, request.getCount());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping
    public ResponseEntity<Void> updateCount(@PathVariable long customerId,
        @RequestParam long productId, @Valid @RequestBody CartItemUpdateRequest request) {
        cartService.updateCount(customerId, productId, request.getCount());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCartItem(@PathVariable long customerId,
        @RequestParam long productId) {
        cartService.deleteCartItem(customerId, productId);
        return ResponseEntity.noContent().build();
    }
}
