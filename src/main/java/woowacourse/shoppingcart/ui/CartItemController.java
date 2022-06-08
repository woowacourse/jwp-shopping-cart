package woowacourse.shoppingcart.ui;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartItemService;
import woowacourse.shoppingcart.dto.cartitem.CartItemAddRequest;
import woowacourse.shoppingcart.dto.cartitem.CartItemAddResponse;
import woowacourse.shoppingcart.dto.cartitem.CartItemCreateRequest;
import woowacourse.shoppingcart.dto.cartitem.CartItemCreateResponse;
import woowacourse.shoppingcart.dto.cartitem.CartItemDeleteRequest;
import woowacourse.shoppingcart.dto.cartitem.CartItemResponse;

@RestController
@RequestMapping("/auth/customer/cartItems")
public class CartItemController {
    private final CartItemService cartItemService;

    public CartItemController(final CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping
    public ResponseEntity<List<CartItemCreateResponse>> createCartItems(
            @AuthenticationPrincipal TokenRequest tokenRequest,
            @RequestBody List<CartItemCreateRequest> cartItemCreateRequests) {
        return ResponseEntity.ok().body(cartItemService.createCartItems(tokenRequest, cartItemCreateRequests));
    }

    @PatchMapping
    public ResponseEntity<CartItemAddResponse> addCartItem(
            @AuthenticationPrincipal TokenRequest tokenRequest,
            @RequestBody CartItemAddRequest cartItemAddRequest) {
        return ResponseEntity.ok().body(cartItemService.addCartItem(tokenRequest, cartItemAddRequest));
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> showCartItems(@AuthenticationPrincipal TokenRequest tokenRequest) {
        return ResponseEntity.ok().body(cartItemService.findCartItemsByCustomerId(tokenRequest));
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteCartItems(@AuthenticationPrincipal TokenRequest tokenRequest,
                                                @RequestBody List<CartItemDeleteRequest> cartItemDeleteRequests) {
        cartItemService.deleteCartItems(tokenRequest, cartItemDeleteRequests);
        return ResponseEntity.noContent().build();
    }
}
