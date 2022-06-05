package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.Request;
import woowacourse.shoppingcart.application.CartService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/customers/cart")
public class CartItemController {
    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Void> addCartItem(@AuthenticationPrincipal int customerId, @RequestBody final CartRequest cartRequest) {
        final Long cartId = cartService.addCart(customerId, cartRequest.getProductId(), cartRequest.getQuantity());
        final URI responseLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{cartId}")
                .buildAndExpand(cartId)
                .toUri();
        return ResponseEntity.created(responseLocation).build();
    }

//    @GetMapping
//    public ResponseEntity<List<Cart>> getCartItems(@PathVariable final String customerName) {
//        return ResponseEntity.ok().body(cartService.findCartsByCustomerName(customerName));
//    }
//
//
//    @DeleteMapping("/{cartId}")
//    public ResponseEntity<Void> deleteCartItem(@PathVariable final String customerName,
//                                         @PathVariable final Long cartId) {
//        cartService.deleteCart(customerName, cartId);
//        return ResponseEntity.noContent().build();
//    }
}
