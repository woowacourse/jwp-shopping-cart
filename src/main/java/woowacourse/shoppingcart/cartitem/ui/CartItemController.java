package woowacourse.shoppingcart.cartitem.ui;

import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.cartitem.application.CartItemService;
import woowacourse.shoppingcart.cartitem.application.dto.AddCartItemDto;
import woowacourse.shoppingcart.cartitem.application.dto.DeleteCartItemDto;
import woowacourse.shoppingcart.cartitem.application.dto.UpdateQuantityDto;
import woowacourse.shoppingcart.cartitem.ui.dto.CartItemQuantityRequest;
import woowacourse.shoppingcart.cartitem.ui.dto.CartItemRequest;
import woowacourse.shoppingcart.cartitem.ui.dto.CartItemResponse;
import woowacourse.shoppingcart.cartitem.ui.dto.DeleteCartItemRequest;

@RestController
@RequestMapping("/api/mycarts")
public class CartItemController {
    private final CartItemService cartItemService;

    public CartItemController(final CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> getCartItems(@AuthenticationPrincipal String email) {
        final List<CartItemResponse> cartItemsResponse = cartItemService.findCartItems(email);
        return ResponseEntity.ok(cartItemsResponse);
    }

    @GetMapping("/{cartItemId}")
    public ResponseEntity<CartItemResponse> getCartItem(@PathVariable Long cartItemId, @AuthenticationPrincipal String email) {
        final CartItemResponse cartItemResponse = cartItemService.findCartItem(email, cartItemId);
        return ResponseEntity.ok(cartItemResponse);
    }

    @PostMapping
    public ResponseEntity<CartItemResponse> addCartItem(@Valid @RequestBody CartItemRequest cartItemRequest, @AuthenticationPrincipal String email) {
        final CartItemResponse cartItemResponse = cartItemService
                .addCartItem(AddCartItemDto.from(cartItemRequest, email));
        return ResponseEntity.created(URI.create("/api/mycarts/" + cartItemResponse.getId())).body(cartItemResponse);
    }

    @PatchMapping
    public ResponseEntity<Void> updateCartItemQuantity(@Valid @RequestBody CartItemQuantityRequest cartItemQuantityRequest,
                                                       @AuthenticationPrincipal String email) {
        cartItemService.updateQuantity(UpdateQuantityDto.from(cartItemQuantityRequest, email));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCartItem(@RequestBody DeleteCartItemRequest deleteCartItemRequest,
                                               @AuthenticationPrincipal String email) {
        cartItemService.deleteCartItem(DeleteCartItemDto.from(deleteCartItemRequest, email));
        return ResponseEntity.noContent().build();
    }
}
