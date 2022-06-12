package woowacourse.shoppingcart.ui;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.dto.AddCartItemRequest;
import woowacourse.shoppingcart.dto.AuthorizedCustomer;
import woowacourse.shoppingcart.dto.DeleteCartItemRequests;
import woowacourse.shoppingcart.dto.FindAllCartItemResponse;
import woowacourse.shoppingcart.dto.Request;
import woowacourse.shoppingcart.dto.UpdateCartItemRequests;
import woowacourse.shoppingcart.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartItemController {

    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<FindAllCartItemResponse> getCartItems(
            @AuthenticationPrincipal final AuthorizedCustomer authorizedCustomer
    ) {
        return ResponseEntity.ok().body(cartService.getAllCartItem(authorizedCustomer.getId()));
    }

    @PostMapping
    public ResponseEntity<Void> addCartItem(
            @Validated(Request.id.class) @RequestBody final AddCartItemRequest addCartItemRequest,
            @AuthenticationPrincipal final AuthorizedCustomer authorizedCustomer
    ) {
        cartService.addCart(authorizedCustomer.getId(), addCartItemRequest);

        return ResponseEntity.created(URI.create("/cart")).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCartItem(
            @AuthenticationPrincipal final AuthorizedCustomer authorizedCustomer,
            @RequestBody final DeleteCartItemRequests deleteCartItemRequests
    ) {
        cartService.deleteCart(authorizedCustomer.getId(), deleteCartItemRequests);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllCartItem(
            @AuthenticationPrincipal final AuthorizedCustomer authorizedCustomer
    ) {
        cartService.deleteAll(authorizedCustomer.getId());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping
    public ResponseEntity<FindAllCartItemResponse> updateCartItem(
            @AuthenticationPrincipal final AuthorizedCustomer authorizedCustomer,
            @RequestBody final UpdateCartItemRequests updateCartItemRequests
    ) {
        final var customerId = authorizedCustomer.getId();

        cartService.update(customerId, updateCartItemRequests);
        final var findAllCartItemResponse = cartService.getAllCartItem(customerId);

        return ResponseEntity.ok().body(findAllCartItemResponse);
    }
}
