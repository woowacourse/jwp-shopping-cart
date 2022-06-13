package woowacourse.shoppingcart.ui;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.dto.CartHasProductDto;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.CartResponse;

@RestController
@RequestMapping("/api/customers/carts")
public class CartItemController {

    private final CartService cartService;

    public CartItemController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartResponse>> getCartItems(@AuthenticationPrincipal final Long customerId) {
        return ResponseEntity.ok().body(cartService.findCartsByCustomerId(customerId));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<CartHasProductDto> existsCartItems(@AuthenticationPrincipal final Long customerId,
                                                             @PathVariable final Long productId) {
        return ResponseEntity.ok().body(new CartHasProductDto(cartService.existsCartItems(customerId, productId)));
    }

    @PostMapping
    public ResponseEntity<Void> addCartItem(@RequestBody final CartRequest request,
                                            @AuthenticationPrincipal Long customerId) {

        final Long cartId = cartService.addCart(request, customerId);
        final URI responseLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{cartId}")
                .buildAndExpand(cartId)
                .toUri();
        return ResponseEntity.created(responseLocation).build();
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCartItem(@AuthenticationPrincipal Long customerId,
                                               @PathVariable final Long cartId) {
        cartService.deleteCart(cartId, customerId);
        return ResponseEntity.noContent().build();
    }
}
