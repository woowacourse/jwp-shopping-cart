package woowacourse.cartitem.ui;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.cartitem.application.CartItemService;
import woowacourse.cartitem.dto.CartItemAddRequest;

@RequestMapping("/api/cartItems")
@RestController
public class CartItemController {

    private final CartItemService cartItemService;

    public CartItemController(final CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    // @GetMapping
    // public ResponseEntity<List<Cart>> getCartItems(@PathVariable final String customerName) {
    //     return ResponseEntity.ok().body(cartItemService.findCartsByCustomerName(customerName));
    // }

    @PostMapping
    public ResponseEntity<Void> addCartItem(
        @AuthenticationPrincipal String username,
        @Valid @RequestBody CartItemAddRequest cartItemAddRequest
    ) {
        final Long cartItemId = cartItemService.addCartItem(username, cartItemAddRequest);
        return ResponseEntity.created(URI.create("/api/cartItems/" + cartItemId)).build();
    }

    // @DeleteMapping("/{cartId}")
    // public ResponseEntity<Void> deleteCartItem(@PathVariable final String customerName,
    //                                      @PathVariable final Long cartId) {
    //     cartItemService.deleteCart(customerName, cartId);
    //     return ResponseEntity.noContent().build();
    // }
}
