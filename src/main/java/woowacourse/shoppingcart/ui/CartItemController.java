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
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CartAdditionRequest;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.dto.CartUpdationRequest;
import woowacourse.shoppingcart.dto.CartsResponse;

@RestController
@RequestMapping("/users/me/carts")
public class CartItemController {
    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Void> addCartItem(@AuthenticationPrincipal Customer customer,
                                            @RequestBody CartAdditionRequest request) {
        cartService.addCart(request.getProductId(), customer);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<CartsResponse> getCartItems(@AuthenticationPrincipal Customer customer) {
        List<Cart> carts = cartService.getCarts(customer);

        List<CartResponse> cartResponses = carts.stream()
                .map(CartResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(new CartsResponse(cartResponses));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<CartResponse> updateCartItem(@AuthenticationPrincipal Customer customer, @Valid @RequestBody
            CartUpdationRequest request, @PathVariable Long productId) {

        Cart cart = cartService.updateProductInCart(customer, request, productId);

        return ResponseEntity.ok(new CartResponse(cart));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteCartItem(@AuthenticationPrincipal Customer customer,
                                               @PathVariable Long productId) {
        cartService.deleteProductInCart(customer, productId);
        return ResponseEntity.noContent().build();
    }
}
