package woowacourse.shoppingcart.cart.ui;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.shoppingcart.cart.application.CartService;
import woowacourse.shoppingcart.cart.domain.Cart;
import woowacourse.shoppingcart.cart.dto.CartItemAdditionRequest;
import woowacourse.shoppingcart.cart.dto.CartItemsResponse;
import woowacourse.shoppingcart.customer.domain.Customer;
import woowacourse.shoppingcart.support.Login;

@RestController
@RequestMapping("/users/me/carts")
public class CartItemController {

    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<CartItemsResponse> getCartItems(@Login final Customer customer) {
        final List<Cart> carts = cartService.findCartsByCustomerName(customer.getNickname());
        final CartItemsResponse response = CartItemsResponse.from(carts);
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

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable final String customerName,
                                               @PathVariable final Long cartId) {
        cartService.deleteCart(customerName, cartId);
        return ResponseEntity.noContent().build();
    }
}
