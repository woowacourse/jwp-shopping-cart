package woowacourse.shoppingcart.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.auth.support.MemberOnly;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.CartItemsResponse;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.dto.Request;
import woowacourse.shoppingcart.application.CartService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/customers/carts")
public class CartItemController {
    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CartResponse addCartItem(@MemberOnly Long customerId, @RequestBody CartRequest cartRequest) {
        return cartService.addCartItem(customerId, cartRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CartItemsResponse getCartItems(@MemberOnly Long customerId) {
        return cartService.findCartItems(customerId);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateQuantity(@MemberOnly Long customerId, @RequestBody CartRequest cartRequest) {
        cartService.updateCartItemQuantity(customerId, cartRequest);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCartItem(@MemberOnly Long customerId, @RequestBody List<Long> productIds) {
        cartService.deleteCartItems(customerId, productIds);
    }
}
