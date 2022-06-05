package woowacourse.shoppingcart.ui;

import java.net.URI;
import java.util.List;

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
import woowacourse.shoppingcart.application.dto.CartItemResponse;
import woowacourse.shoppingcart.application.dto.CartResponse;
import woowacourse.shoppingcart.ui.dto.FindCustomerRequest;
import woowacourse.shoppingcart.ui.dto.ProductRequest;
import woowacourse.shoppingcart.ui.dto.Request;

@RestController
@RequestMapping("/api/customers/me/cart-items")
public class CartItemController {

    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> getCartItems(@AuthenticationPrincipal FindCustomerRequest customerRequest) {
        final CartResponse cartsResponse = cartService.findCartsByCustomerName(customerRequest.getName());
        return ResponseEntity.ok().body(cartsResponse.getItemResponses());
    }

    @PostMapping
    public ResponseEntity<Void> addCartItem(
        @Validated(Request.id.class) @RequestBody final ProductRequest request,
        @AuthenticationPrincipal FindCustomerRequest customerRequest) {
        final Long cartId = cartService.addCart(request.getId(), customerRequest.getName());
        final URI responseLocation = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{cartId}")
            .buildAndExpand(cartId)
            .toUri();
        return ResponseEntity.created(responseLocation).build();
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCartItem(@AuthenticationPrincipal FindCustomerRequest customerRequest,
        @PathVariable final Long cartId) {
        cartService.deleteCart(customerRequest.getName(), cartId);
        return ResponseEntity.noContent().build();
    }
}
