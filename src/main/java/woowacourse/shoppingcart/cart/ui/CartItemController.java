package woowacourse.shoppingcart.cart.ui;

import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.shoppingcart.cart.application.CartService;
import woowacourse.shoppingcart.cart.domain.Cart;
import woowacourse.shoppingcart.cart.domain.CartItem;
import woowacourse.shoppingcart.cart.dto.CartItemAdditionRequest;
import woowacourse.shoppingcart.cart.dto.CartItemResponse;
import woowacourse.shoppingcart.cart.dto.CartItemsResponse;
import woowacourse.shoppingcart.cart.dto.QuantityChangingRequest;
import woowacourse.shoppingcart.customer.domain.Customer;
import woowacourse.shoppingcart.support.Login;

@RestController
@RequestMapping("/users/me/cartItems")
public class CartItemController {

    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<CartItemsResponse> getCartItems(@Login final Customer customer) {
        final Cart cartItems = cartService.findCartBy(customer);
        final CartItemsResponse response = CartItemsResponse.from(cartItems);
        return ResponseEntity
                .ok()
                .body(response);
    }

    @PostMapping
    public ResponseEntity<Void> addCartItem(@RequestBody final CartItemAdditionRequest request,
                                            @Login final Customer customer) {
        cartService.addCart(request.getProductId(), customer);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PutMapping("/{productId}")
    public ResponseEntity<CartItemResponse> changeQuantity(@Login final Customer customer,
                                                           @PathVariable final Long productId,
                                                           @RequestBody @Valid final QuantityChangingRequest request) {
        final CartItem changedCartItem = cartService.changeQuantity(customer, productId, request);
        final CartItemResponse response = new CartItemResponse(changedCartItem);
        return ResponseEntity
                .ok(response);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteCartItem(@Login final Customer customer,
                                               @PathVariable final Long productId) {
        cartService.deleteCartBy(customer, productId);
        return ResponseEntity
                .noContent()
                .build();
    }
}
