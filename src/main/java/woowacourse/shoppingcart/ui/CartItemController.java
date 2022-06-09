package woowacourse.shoppingcart.ui;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.dto.CartsResponse;
import woowacourse.shoppingcart.dto.DeleteCartRequest;

@RestController
@RequestMapping("/customers/carts")
public class CartItemController {

    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<CartsResponse> getCartItems(@AuthenticationPrincipal Long id) {
        List<CartResponse> carts = cartService.findCartsByCustomerId(id).stream()
                .map(CartResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(new CartsResponse(carts));
    }

    @PostMapping
    public ResponseEntity<Void> addCartItem(@AuthenticationPrincipal Long id, @RequestBody CartRequest cartRequest) {
        final Long cartId = cartService.addCart(id, cartRequest.getProductId(), cartRequest.getQuantity());
        final URI responseLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{cartId}")
                .buildAndExpand(cartId)
                .toUri();
        return ResponseEntity.created(responseLocation).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCartItem(@AuthenticationPrincipal Long id,
                                               @RequestBody DeleteCartRequest deleteCartRequest) {
        cartService.deleteCart(id, deleteCartRequest.getProductIds());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping
    public ResponseEntity<Void> updateCartItem(@AuthenticationPrincipal Long id, @RequestBody CartRequest cartRequest) {
        cartService.updateCart(id, cartRequest.getProductId(), cartRequest.getQuantity());
        return ResponseEntity.ok().build();
    }
}
