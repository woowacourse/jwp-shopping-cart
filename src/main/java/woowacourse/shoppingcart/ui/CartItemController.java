package woowacourse.shoppingcart.ui;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.dto.cart.AddCartItemRequest;
import woowacourse.shoppingcart.dto.cart.CartDto;
import woowacourse.shoppingcart.dto.cart.DeleteCartItemRequest;
import woowacourse.shoppingcart.dto.cart.UpdateCartItemRequest;

@RestController
@RequestMapping("/api/customer/cartItems")
public class CartItemController {
    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartDto>> getCartItems(@AuthenticationPrincipal final Long customerId) {
        final List<CartItem> cartItems = cartService.findCartItemsByCustomerId(customerId);
        final List<CartDto> cartDtos = cartItems.stream()
                .map(CartDto::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(cartDtos);
    }

    @PostMapping
    public ResponseEntity<Void> enrollCartItem(@AuthenticationPrincipal final Long customerId,
                                               @Valid @RequestBody final AddCartItemRequest request) {
        cartService.enrollCartItem(customerId, request.getProductId());
        final URI responseLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .build()
                .toUri();
        return ResponseEntity.created(responseLocation).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCartItem(@AuthenticationPrincipal final Long customerId,
                                               @Valid @RequestBody DeleteCartItemRequest request) {
        cartService.deleteItems(customerId, request.getCartIds());
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateCartItem(@AuthenticationPrincipal final Long customerId,
                                               @Valid @RequestBody UpdateCartItemRequest request) {
        cartService.update(customerId, request.getProductId(), request.getQuantity());
        return ResponseEntity.noContent().build();
    }
}
