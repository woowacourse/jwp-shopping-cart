package woowacourse.shoppingcart.ui;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.dto.CartItemResponses;
import woowacourse.shoppingcart.dto.CartRequest;

@RestController
@RequestMapping("/api/customers/cart")
public class CartItemController {
    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Void> addCartItem(@AuthenticationPrincipal final int customerId, @RequestBody final CartRequest cartRequest) {
        final Long cartId = cartService.addCart(customerId, cartRequest.getProductId(), cartRequest.getQuantity());
        final URI responseLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{cartId}")
                .buildAndExpand(cartId)
                .toUri();
        return ResponseEntity.created(responseLocation).build();
    }

    @GetMapping
    public ResponseEntity<CartItemResponses> getCartItems(@AuthenticationPrincipal final int customerId) {
        return ResponseEntity.ok().body(cartService.findCartsByCustomerId(customerId));
    }
//
//
//    @DeleteMapping("/{cartId}")
//    public ResponseEntity<Void> deleteCartItem(@PathVariable final String customerName,
//                                         @PathVariable final Long cartId) {
//        cartService.deleteCart(customerName, cartId);
//        return ResponseEntity.noContent().build();
//    }
}
