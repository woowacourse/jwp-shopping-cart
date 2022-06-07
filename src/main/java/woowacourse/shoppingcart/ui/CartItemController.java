package woowacourse.shoppingcart.ui;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.CartItemsRequest;
import woowacourse.shoppingcart.dto.CartItemsResponse;
import woowacourse.shoppingcart.dto.ProductResponse;

@RestController
@RequestMapping("/cart")
public class CartItemController {
    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<CartItemsResponse> getCartItems(@AuthenticationPrincipal final String username) {
        Product product1 = new Product(1L, null, 0, null);
        Product product2 = new Product(2L, null, 0, null);

        ProductResponse productResponse1 = ProductResponse.from(product1);
        ProductResponse productResponse2 = ProductResponse.from(product2);

        CartItemResponse cartItemResponse1 = new CartItemResponse(productResponse1, 1);
        CartItemResponse cartItemResponse2 = new CartItemResponse(productResponse2, 1);

        CartItemsResponse cartItemsResponse = new CartItemsResponse(List.of(cartItemResponse1, cartItemResponse2));
        //cartService.findCartsByCustomerName(username);
        return ResponseEntity.ok().body(cartItemsResponse);
    }

    @PostMapping("/{productId}")
    public ResponseEntity<Void> addCartItem(@AuthenticationPrincipal final String username,
                                            @PathVariable final Long productId) {
        cartService.addCart(productId, username);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/products")
    public ResponseEntity<Void> deleteCartItems(@AuthenticationPrincipal final String username,
                                               @RequestBody final CartItemsRequest cartItemsRequest) {
        //cartService.deleteCartItems(cartItemsRequest, username);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCart(@AuthenticationPrincipal final String username) {
        //cartService.deleteCart(username);
        return ResponseEntity.noContent().build();
    }
}
