package woowacourse.shoppingcart.ui;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.dto.response.GetCartItemResponse;
import woowacourse.shoppingcart.dto.response.GetCartItemsResponse;

@RestController
@RequestMapping("/cart")
public class CartItemController {
    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<GetCartItemsResponse> getCartItems(@AuthenticationPrincipal Long customerId) {
        List<GetCartItemResponse> getCartItemResponses = cartService.findCartItems(customerId)
                .stream()
                .map(it -> new GetCartItemResponse(it.getId(), it.getName(), it.getPrice(), it.getThumbnail()))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(new GetCartItemsResponse(getCartItemResponses));
    }

    @PostMapping("{productId}")
    public ResponseEntity<Void> addCartItem(@AuthenticationPrincipal Long customerId,
                                      @PathVariable final Long productId) {
        cartService.addCart(customerId, productId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/products")
    public ResponseEntity<Void> deleteCartItem(@PathVariable final String customerName,
                                         @PathVariable final Long cartId) {
        cartService.deleteCart(customerName, cartId);
        return ResponseEntity.noContent().build();
    }
}
