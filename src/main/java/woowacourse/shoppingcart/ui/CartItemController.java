package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.CartItemAddRequest;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.CartItemUpdateRequest;
import woowacourse.shoppingcart.dto.Request;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class CartItemController {
    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/users/me/carts")
    public ResponseEntity<List<Cart>> getCartItems(@AuthenticationPrincipal Customer customer) {
        return ResponseEntity.ok(cartService.findCartsByCustomer(customer));
    }

    @GetMapping("/api/customers/{customerName}/carts")
    public ResponseEntity<List<Cart>> getCartItems(@PathVariable final String customerName) {
        return ResponseEntity.ok().body(cartService.findCartsByCustomerName(customerName));
    }

    @PostMapping("/users/me/carts")
    public ResponseEntity<Void> addCartItem2(@RequestBody CartItemAddRequest cartItemAddRequest, @AuthenticationPrincipal Customer customer) {
        cartService.addCart(cartItemAddRequest.getProductId(), customer);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/api/customers/{customerName}/carts")
    public ResponseEntity<Void> addCartItem(@Validated(Request.id.class) @RequestBody final Product product,
                                            @PathVariable final String customerName) {
        final Long cartId = cartService.addCart(product.getId(), customerName);
        final URI responseLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{cartId}")
                .buildAndExpand(cartId)
                .toUri();
        return ResponseEntity.created(responseLocation).build();
    }

    @DeleteMapping("/users/me/carts/{productId}")
    public ResponseEntity<Void> deleteCartItem2(@PathVariable final Long productId, @AuthenticationPrincipal Customer customer) {
        cartService.deleteCart(customer, productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/api/customers/{customerName}/carts/{cartId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable final String customerName,
                                               @PathVariable final Long cartId) {
        cartService.deleteCart(customerName, cartId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/users/me/carts/{productId}")
    public ResponseEntity<CartItemResponse> updateCartItemQuantity(@PathVariable Long productId,
                                                                   @RequestBody CartItemUpdateRequest request,
                                                                   @AuthenticationPrincipal Customer customer) {
        Cart cartItem = cartService.updateQuantity(request, customer, productId);
        return ResponseEntity.ok(CartItemResponse.from(cartItem));
    }
}
