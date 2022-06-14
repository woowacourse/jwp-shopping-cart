package woowacourse.shoppingcart.ui;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.dto.ProductIdsRequest;

@RestController
@RequestMapping("/customers/me/carts")
public class CartItemController {

    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CartResponse> getCartItems(@AuthenticationPrincipal Long customerId) {
        return cartService.findCartProductsByCustomerId(customerId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addCartItem(@AuthenticationPrincipal Long customerId, @RequestBody CartRequest cartRequest) {
        cartService.addCart(customerId, cartRequest);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCartItem(@AuthenticationPrincipal Long customerId, @RequestBody ProductIdsRequest productIds) {
        cartService.deleteCart(customerId, productIds);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateCartItemQuantity(@AuthenticationPrincipal Long customerId, @RequestBody CartRequest cartRequest) {
        cartService.updateCartQuantity(customerId, cartRequest);
    }
}
