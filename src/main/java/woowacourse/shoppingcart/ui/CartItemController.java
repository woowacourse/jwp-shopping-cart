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
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.CartItemSaveRequest;
import woowacourse.shoppingcart.dto.Request;
import woowacourse.shoppingcart.dto.customer.LoginCustomer;

@RestController
@RequestMapping("/api/customers/me/cart-items")
public class CartItemController {
    private final CartService cartService;

    public CartItemController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> getCartItems(@AuthenticationPrincipal LoginCustomer loginCustomer) {
        List<CartItemResponse> cartItemResponses = cartService.findCartsByCustomerName(loginCustomer);
        return ResponseEntity.ok().body(cartItemResponses);
    }

    @PostMapping
    public ResponseEntity<Void> addCartItem(
            @Validated(Request.id.class) @RequestBody CartItemSaveRequest cartItemSaveRequest,
            @AuthenticationPrincipal LoginCustomer loginCustomer) {
        Long cartId = cartService.addCart(cartItemSaveRequest, loginCustomer);
        URI responseLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{cartId}")
                .buildAndExpand(cartId)
                .toUri();
        return ResponseEntity.created(responseLocation).build();
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCartItem(
            @AuthenticationPrincipal LoginCustomer loginCustomer, @PathVariable Long cartId) {
        cartService.deleteCart(loginCustomer, cartId);
        return ResponseEntity.noContent().build();
    }
}
