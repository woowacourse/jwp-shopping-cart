package woowacourse.shoppingcart.controller;

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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.auth.support.UserNameResolver;
import woowacourse.shoppingcart.domain.customer.UserName;
import woowacourse.shoppingcart.dto.request.CreateCartItemRequest;
import woowacourse.shoppingcart.dto.request.EditCartItemQuantityRequest;
import woowacourse.shoppingcart.dto.response.CartItemResponse;
import woowacourse.shoppingcart.service.CartItemService;

@RestController
@RequestMapping("/api/customers/me/carts")
public class CartItemController {

    private final CartItemService cartService;

    public CartItemController(final CartItemService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> findCartItems(@UserNameResolver final UserName customerName) {
        return ResponseEntity.ok().body(cartService.findCartsByCustomerName(customerName));
    }

    @PostMapping
    public ResponseEntity<Void> addCartItem(@Valid @RequestBody final CreateCartItemRequest request,
                                            @UserNameResolver final UserName customerName) {
        final Long cartId = cartService.addCart(customerName, request);
        final URI responseLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{cartId}")
                .buildAndExpand(cartId)
                .toUri();
        return ResponseEntity.created(responseLocation).build();
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCartItem(@UserNameResolver final UserName customerName,
                                               @PathVariable final Long cartId) {
        cartService.deleteCart(customerName, cartId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{cartId}")
    public ResponseEntity<Void> editQuantity(@UserNameResolver final UserName customerName,
                                             @PathVariable final Long cartId,
                                             @RequestBody final EditCartItemQuantityRequest request) {
        cartService.editQuantity(customerName, cartId, request);
        return ResponseEntity.ok().build();
    }
}
