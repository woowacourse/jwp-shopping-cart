package woowacourse.shoppingcart.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.CartItemDto;
import woowacourse.shoppingcart.dto.CartItemIds;
import woowacourse.shoppingcart.dto.addCartItemRequest;
import woowacourse.shoppingcart.dto.updateCartItemRequest;
import woowacourse.shoppingcart.service.CartService;

@RestController
@RequestMapping("/api/mycarts")
public class CartItemController {

    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<CartItemDto> addCartItem(@AuthenticationPrincipal Customer customer,
            @RequestBody final addCartItemRequest request) {
        final Long productId = request.getProductId();
        final int quantity = request.getQuantity();

        final Long cartItemId = cartService.addCart(customer, productId, quantity);
        final CartItem cartItem = cartService.findById(customer, cartItemId);
        return ResponseEntity.created(URI.create("/api/mycarts/" + cartItemId)).body(CartItemDto.of(cartItem));
    }

    @PatchMapping
    public ResponseEntity<Void> updateQuantity(@AuthenticationPrincipal Customer customer,
            @RequestBody final updateCartItemRequest request) {
        final Long cartItemId = request.getCartItemId();
        final int quantity = request.getQuantity();
        cartService.updateQuantity(customer, cartItemId, quantity);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<CartItemDto>> getCartItems(@AuthenticationPrincipal Customer customer) {
        final List<CartItem> cartItems = cartService.findCartItemsByCustomer(customer);
        return ResponseEntity.ok().body(CartItemDto.of(cartItems));
    }

    @GetMapping("/{cartItemId}")
    public ResponseEntity<CartItemDto> getCartItem(@AuthenticationPrincipal Customer customer,
            @PathVariable Long cartItemId) {
        final CartItem cartItem = cartService.findById(customer, cartItemId);
        return ResponseEntity.ok().body(CartItemDto.of(cartItem));
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteCartItems(@AuthenticationPrincipal Customer customer,
            @RequestBody final CartItemIds cartItemIds) {
        cartService.deleteCart(customer, cartItemIds);
        return ResponseEntity.noContent().build();
    }
}
