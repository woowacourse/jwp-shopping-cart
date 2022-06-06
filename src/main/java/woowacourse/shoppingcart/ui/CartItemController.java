package woowacourse.shoppingcart.ui;

import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.dto.ProductRequest;
import woowacourse.shoppingcart.dto.Request;

@RestController
@RequestMapping
public class CartItemController {
    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/api/members/me/carts")
    public ResponseEntity<List<CartResponse>> getCartItems(@AuthenticationPrincipal Long memberId) {
        return ResponseEntity.ok(cartService.findCartsById(memberId));
    }

    @PostMapping("/api/members/me/carts")
    public ResponseEntity<Void> addCartItem(@AuthenticationPrincipal final Long memberId,
                                            @RequestBody final CartRequest cartRequest) {
        final Long id = cartService.addCart(memberId, cartRequest);
        return ResponseEntity.created(URI.create("/api/members/me/carts/" + id)).build();
    }

    @DeleteMapping("/api/customers/{customerName}/carts/{cartId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable final String customerName,
                                         @PathVariable final Long cartId) {
        cartService.deleteCart(customerName, cartId);
        return ResponseEntity.noContent().build();
    }
}
