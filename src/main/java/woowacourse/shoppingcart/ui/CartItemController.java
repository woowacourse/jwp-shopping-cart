package woowacourse.shoppingcart.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.dto.AddCartItemRequest;
import woowacourse.shoppingcart.dto.AuthorizedCustomer;
import woowacourse.shoppingcart.dto.DeleteCartItemIdsRequest;
import woowacourse.shoppingcart.dto.DeleteCartItemRequest;
import woowacourse.shoppingcart.dto.FindAllCartItemResponse;
import woowacourse.shoppingcart.dto.Request;

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

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCartItem(
            @AuthenticationPrincipal final AuthorizedCustomer authorizedCustomer,
            @RequestBody final DeleteCartItemRequest deleteCartItemRequest
    ) {
        var deleteCartItemIdsRequest = new DeleteCartItemIdsRequest(deleteCartItemRequest);
        cartService.deleteCart(authorizedCustomer.getId(), deleteCartItemIdsRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllCartItem(
            @AuthenticationPrincipal final AuthorizedCustomer authorizedCustomer
    ) {
        cartService.deleteAll(authorizedCustomer.getId());
        return ResponseEntity.noContent().build();
    }
}
