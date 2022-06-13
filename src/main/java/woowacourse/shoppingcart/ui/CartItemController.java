package woowacourse.shoppingcart.ui;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
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
import woowacourse.shoppingcart.application.CartItemService;
import woowacourse.shoppingcart.dto.CartItemQuantityUpdateRequest;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.CartItemSaveRequest;
import woowacourse.shoppingcart.dto.customer.LoginCustomer;

@RestController
@RequestMapping("/api/customers/me/cart-items")
public class CartItemController {
    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> getCartItems(@AuthenticationPrincipal LoginCustomer loginCustomer) {
        List<CartItemResponse> cartItemResponses = cartItemService.findCartsByCustomerName(loginCustomer);
        return ResponseEntity.ok().body(cartItemResponses);
    }

    @PostMapping
    public ResponseEntity<Void> addCartItem(
            @Valid @RequestBody CartItemSaveRequest cartItemSaveRequest,
            @AuthenticationPrincipal LoginCustomer loginCustomer) {
        Long cartId = cartItemService.addCart(cartItemSaveRequest, loginCustomer);
        URI responseLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{cartId}")
                .buildAndExpand(cartId)
                .toUri();
        return ResponseEntity.created(responseLocation).build();
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> deleteCartItem(
            @AuthenticationPrincipal LoginCustomer loginCustomer, @PathVariable Long cartItemId) {
        cartItemService.deleteCart(loginCustomer, cartItemId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{cartItemId}")
    public ResponseEntity<Void> updateQuantity(@PathVariable Long cartItemId,
            @Valid @RequestBody CartItemQuantityUpdateRequest cartItemQuantityUpdateRequest,
            @AuthenticationPrincipal LoginCustomer loginCustomer) {
        cartItemService.updateQuantity(loginCustomer, cartItemId, cartItemQuantityUpdateRequest);
        return ResponseEntity.noContent().build();
    }
}
