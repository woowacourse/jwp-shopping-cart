package woowacourse.shoppingcart.ui;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.dto.Request;

@RestController
@RequestMapping("/api/carts")
public class CartItemController {
    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartResponse>> getCartItems(@AuthenticationPrincipal final String email) {
        List<Cart> carts = cartService.findCartsByEmail(email);
        List<CartResponse> cartResponses = carts.stream()
                .map(CartResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(cartResponses);
    }

    @PostMapping("/products")
    public ResponseEntity<Void> addCartItem(@Validated(Request.id.class) @RequestBody final CartRequest cartRequest,
                                            @AuthenticationPrincipal final String email) {
        final Long cartId = cartService.addCart(cartRequest, email);
        final URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + cartId)
                .build().toUri();
        return ResponseEntity.created(uri).build();
    }

    @PatchMapping("/products")
    public ResponseEntity<List<CartResponse>> modifyCartQuantity(
            @Validated(Request.id.class) @RequestBody final CartRequest cartRequest,
            @AuthenticationPrincipal final String email) {
        List<Cart> carts = cartService.modifyCartQuantity(cartRequest, email);
        List<CartResponse> cartResponses = carts.stream()
                .map(CartResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(cartResponses);
    }

    @DeleteMapping("/products")
    public ResponseEntity<List<CartResponse>> deleteCartItem(@AuthenticationPrincipal final String email,
                                                             @RequestParam final Long productId) {
        List<Cart> carts = cartService.deleteCart(email, productId);
        List<CartResponse> cartResponses = carts.stream()
                .map(CartResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(cartResponses);
    }
}
