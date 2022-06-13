package woowacourse.shoppingcart.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.auth.controller.CustomerId;
import woowacourse.shoppingcart.dto.request.CartItemRequest;
import woowacourse.shoppingcart.dto.response.CartItemResponse;
import woowacourse.shoppingcart.dto.response.ProductExistingInCartResponse;
import woowacourse.shoppingcart.service.CartService;

@RestController
@RequestMapping("/api/customers/cart")
public class CartItemController {
    private final CartService cartService;

    public CartItemController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> getCartItems(@CustomerId Long customerId) {
        return ResponseEntity.ok().body(cartService.findCartItemsByCustomerId(customerId));
    }

    @PostMapping
    public ResponseEntity<Void> addCartItem(@RequestBody CartItemRequest cartItemRequest, @CustomerId Long customerId) {
        Long cartItemId = cartService.addCart(cartItemRequest, customerId);
        URI responseLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{cartItemId}")
                .buildAndExpand(cartItemId)
                .toUri();
        return ResponseEntity.created(responseLocation).build();
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> deleteCartItem(@CustomerId Long customerId, @PathVariable Long cartItemId) {
        cartService.deleteCart(customerId, cartItemId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{cartItemId}")
    public ResponseEntity<Void> updateCartItem(@RequestBody CartItemRequest cartItemRequest,
                                               @PathVariable Long cartItemId,
                                               @CustomerId Long customerId) {
        cartService.updateCartItem(customerId, cartItemId, cartItemRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductExistingInCartResponse> checkIsProductExisting(@CustomerId Long customerId,
                                                                                @PathVariable Long productId) {
        ProductExistingInCartResponse productExisting = cartService.isProductExisting(customerId, productId);
        return ResponseEntity.ok(productExisting);
    }
}
