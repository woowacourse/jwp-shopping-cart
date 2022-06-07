package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.dto.AddCartItemRequest;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.service.CartService;

import java.net.URI;

@RestController
@RequestMapping("/cart")
public class CartItemController {
    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<CartResponse> getCartItems(@AuthenticationPrincipal String username) {
        return ResponseEntity.ok().body(cartService.findByUserName(username));
    }

    @PostMapping
    public ResponseEntity addItem(@AuthenticationPrincipal String username, @RequestBody AddCartItemRequest addCartItemRequest) {
        cartService.addItem(username, addCartItemRequest);
        return ResponseEntity.created(URI.create("/cart")).build();
    }
//
//    @PostMapping
//    public ResponseEntity<Void> addCartItem(@Validated(Request.id.class) @RequestBody final Product product,
//                                      @PathVariable final String customerName) {
//        final Long cartId = cartService.addCart(product.getId(), customerName);
//        final URI responseLocation = ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .path("/{cartId}")
//                .buildAndExpand(cartId)
//                .toUri();
//        return ResponseEntity.created(responseLocation).build();
//    }
//
//    @DeleteMapping("/{cartId}")
//    public ResponseEntity<Void> deleteCartItem(@PathVariable final String customerName,
//                                         @PathVariable final Long cartId) {
//        cartService.deleteCart(customerName, cartId);
//        return ResponseEntity.noContent().build();
//    }
}
