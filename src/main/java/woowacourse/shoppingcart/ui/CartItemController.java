package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.shoppingcart.dto.cart.CartItemRequest;
import woowacourse.shoppingcart.dto.cart.CartItemResponse;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.dto.cart.CartsResponse;
import woowacourse.shoppingcart.dto.cart.RemovedCartItemsRequest;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@RestController
@RequestMapping("/customers/carts")
public class CartItemController {
    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<CartsResponse> findCartItems(HttpServletRequest request) {
        String token = AuthorizationExtractor.extract(request);
        CartsResponse cartsResponse = cartService.findCartItems(token);
        return ResponseEntity.ok().body(cartsResponse);
    }



    @PostMapping
    public ResponseEntity<CartItemResponse> addCartItem(HttpServletRequest request, @RequestBody CartItemRequest cartItemRequest) {
        String token = AuthorizationExtractor.extract(request);
        CartItemResponse cartResponse = cartService.addCartItem(token, cartItemRequest);
        return ResponseEntity.created(URI.create("/customers/carts")).body(cartResponse);
    }

    @DeleteMapping
    public ResponseEntity<Void> removeCartItems(HttpServletRequest request, @RequestBody RemovedCartItemsRequest removedCartItemsRequest) {
        String token = AuthorizationExtractor.extract(request);
        cartService.removeCartItems(token, removedCartItemsRequest);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping
    public ResponseEntity<Void> editCartItem(HttpServletRequest request, @RequestBody CartItemRequest cartItemRequest) {
        String token = AuthorizationExtractor.extract(request);
        cartService.editCartItem(token, cartItemRequest);
        return ResponseEntity.ok().build();
    }
}
