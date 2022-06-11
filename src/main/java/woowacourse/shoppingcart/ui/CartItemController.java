package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.dto.ProductRequest;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/customers/me/carts")
public class CartItemController {
    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartResponse>> getCartItems(@AuthenticationPrincipal final Customer customer) {
        return ResponseEntity.ok().body(cartService.findAllByCustomerName(customer.getUserName()));
    }

    @PostMapping
    public ResponseEntity<Void> addCartItem(@AuthenticationPrincipal final Customer customer,
                                            @Valid @RequestBody final ProductRequest.OnlyId productRequest) {
        final Long cartId = cartService.addCart(productRequest.getId(), customer.getUserName());
        final URI responseLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{cartId}")
                .buildAndExpand(cartId)
                .toUri();
        return ResponseEntity.created(responseLocation).build();
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCartItem(@AuthenticationPrincipal final Customer customer,
                                               @PathVariable final Long cartId) {
        cartService.delete(customer.getUserName(), cartId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{cartId}")
    public ResponseEntity<Void> updateQuantity(@AuthenticationPrincipal final Customer customer,
                                               @PathVariable final Long cartId,
                                               @Valid @RequestBody final CartRequest cartRequest) {
        cartService.updateQuantity(customer.getUserName(), cartId, cartRequest.getQuantity());
        return ResponseEntity.noContent().build();
    }
}
