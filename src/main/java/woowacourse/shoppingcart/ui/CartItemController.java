package woowacourse.shoppingcart.ui;

import java.util.List;
import java.util.stream.Collectors;
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
import woowacourse.auth.support.Auth;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.domain.cart.Cart;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.CartAdditionRequest;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.dto.CartUpdationRequest;
import woowacourse.shoppingcart.dto.CartsResponse;

@RestController
@RequestMapping("/users/me/carts")
public class CartItemController {
    private final CartService cartService;
    private final CustomerService customerService;

    public CartItemController(final CartService cartService,
                              CustomerService customerService) {
        this.cartService = cartService;
        this.customerService = customerService;
    }

    @Auth
    @PostMapping
    public ResponseEntity<Void> addCartItem(@AuthenticationPrincipal String email,
                                            @RequestBody CartAdditionRequest request) {
        Customer customer = customerService.getByEmail(email);
        cartService.addCart(request.getProductId(), customer);
        return ResponseEntity.noContent().build();
    }

    @Auth
    @GetMapping
    public ResponseEntity<CartsResponse> getCartItems(@AuthenticationPrincipal String email) {
        Customer customer = customerService.getByEmail(email);
        List<Cart> carts = cartService.getCarts(customer);

        List<CartResponse> cartResponses = carts.stream()
                .map(CartResponse::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(new CartsResponse(cartResponses));
    }

    @Auth
    @PutMapping("/{productId}")
    public ResponseEntity<CartResponse> updateCartItem(@AuthenticationPrincipal String email, @Valid @RequestBody
            CartUpdationRequest request, @PathVariable Long productId) {

        Customer customer = customerService.getByEmail(email);
        Cart cart = cartService.updateProductInCart(customer, request, productId);

        return ResponseEntity.ok(CartResponse.from(cart));
    }

    @Auth
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteCartItem(@AuthenticationPrincipal String email,
                                               @PathVariable Long productId) {
        Customer customer = customerService.getByEmail(email);
        cartService.deleteProductInCart(customer, productId);
        return ResponseEntity.noContent().build();
    }
}
