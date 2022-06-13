package woowacourse.shoppingcart.ui;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.MemberOnly;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.dto.CartItemsResponse;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.dto.ProductsRequest;

@RestController
@RequestMapping("/customers/carts")
public class CartController {
    private final CartService cartService;

    public CartController(final CartService cartService) {
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
    public void deleteCartItem(@MemberOnly Long customerId, @RequestBody ProductsRequest productsRequest) {
        cartService.deleteCartItems(customerId, productsRequest);
    }
}
