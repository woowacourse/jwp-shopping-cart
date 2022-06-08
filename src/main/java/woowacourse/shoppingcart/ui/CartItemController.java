package woowacourse.shoppingcart.ui;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartItemService;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.cart.CartItemCountRequest;
import woowacourse.shoppingcart.dto.cart.CartItemCreateRequest;
import woowacourse.shoppingcart.dto.cart.CartItemDto;
import woowacourse.shoppingcart.exception.ForbiddenAccessException;

@RestController
@RequestMapping("/api/customers/{customerId}/carts")
public class CartItemController {

    private final CartItemService cartService;

    public CartItemController(final CartItemService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartItemDto>> getCartItems(@AuthenticationPrincipal final Customer customer,
                                                          @PathVariable final Long customerId) {
        validateAuthorizedUser(customerId, customer);
        return ResponseEntity.ok().body(cartService.findCartsByCustomerId(customerId));
    }

    @PostMapping
    public ResponseEntity<Void> addCartItem(@RequestBody final CartItemCreateRequest request,
                                            @PathVariable final Long customerId,
                                            @AuthenticationPrincipal final Customer customer) {
        validateAuthorizedUser(customerId, customer);
        cartService.addCartItem(customerId, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCartItem(@PathVariable final Long customerId,
                                               @RequestParam final Long productId,
                                               @AuthenticationPrincipal final Customer customer) {
        validateAuthorizedUser(customerId, customer);
        cartService.deleteCartItem(customerId, productId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping
    public ResponseEntity<Void> updateCartItemCount(@PathVariable final Long customerId,
                                                    @RequestParam final Long productId,
                                                    @RequestBody final CartItemCountRequest request,
                                                    @AuthenticationPrincipal final Customer customer) {
        validateAuthorizedUser(customerId, customer);
        cartService.updateCount(customerId, productId, request.getCount());
        return ResponseEntity.ok().build();
    }

    private void validateAuthorizedUser(long id, Customer customer) {
        if (!customer.getId().equals(id)) {
            throw new ForbiddenAccessException();
        }
    }
}
