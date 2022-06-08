package woowacourse.shoppingcart.ui;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.dto.CartItemRequest;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.FindCustomerRequest;
import woowacourse.shoppingcart.dto.Request;
import woowacourse.shoppingcart.dto.UpdateCartItemRequest;

@RestController
@RequestMapping("/api/customers/me/cart-items")
public class CartItemController {

    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> getCartItems(
            @AuthenticationPrincipal FindCustomerRequest customerRequest) {
        return ResponseEntity.ok().body(cartService.findCartsByCustomerName(customerRequest.getName()));
    }

    @PostMapping
    public ResponseEntity<Void> addCartItem(@Validated(Request.id.class) @RequestBody final CartItemRequest request,
            @AuthenticationPrincipal FindCustomerRequest customerRequest) {
        final Long cartItemId = cartService.addCart(request.getProductId(), request.getQuantity(),
                customerRequest.getName());
        final URI responseLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{cartItemId}")
                .buildAndExpand(cartItemId)
                .toUri();
        return ResponseEntity.created(responseLocation).build();
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> deleteCartItem(@AuthenticationPrincipal FindCustomerRequest customerRequest,
            @PathVariable final Long cartItemId) {
        cartService.deleteCart(customerRequest.getName(), cartItemId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{cartItemId}")
    public ResponseEntity<Void> updateCartItem(@AuthenticationPrincipal FindCustomerRequest customerRequest,
            @RequestBody final UpdateCartItemRequest updateCartItemRequest,
            @PathVariable final Long cartItemId) {
        cartService.updateCartItem(customerRequest.getName(), updateCartItemRequest.getQuantity(), cartItemId);
        return ResponseEntity.noContent().build();
    }
}
