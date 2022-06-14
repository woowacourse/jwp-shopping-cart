package woowacourse.shoppingcart.ui;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.dto.AddCartItemRequest;
import woowacourse.shoppingcart.dto.PutCartItemRequest;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/members/me/carts")
public class CartItemController {
    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Cart> getCarts(@AuthenticationPrincipal long memberId) {
        return cartService.findCartsByMemberId(memberId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addCartItem(@AuthenticationPrincipal long memberId, @Valid @RequestBody AddCartItemRequest request) {
        cartService.addCartItem(memberId, request);
    }

    @PutMapping("/{cartId}")
    @ResponseStatus(HttpStatus.OK)
    public void putCartItem(@AuthenticationPrincipal long memberId,
                            @PathVariable long cartId,
                            @Valid @RequestBody PutCartItemRequest request) {
        cartService.updateCartItem(memberId, cartId, request);
    }


    @DeleteMapping("/{cartId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCartItem(@AuthenticationPrincipal long memberId, @PathVariable long cartId) {
        cartService.deleteCart(memberId, cartId);
    }

}
