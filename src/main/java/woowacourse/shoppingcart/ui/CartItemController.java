package woowacourse.shoppingcart.ui;

import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartItemService;
import woowacourse.shoppingcart.dto.CartItemCreateRequest;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.CartItemUpdateRequest;
import woowacourse.shoppingcart.dto.LoginCustomer;

@RestController
@RequestMapping("/customers/carts")
public class CartItemController {
    private final CartItemService cartItemService;

    public CartItemController(final CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping
    public ResponseEntity<CartItemResponse> add(@AuthenticationPrincipal LoginCustomer loginCustomer,
                                                @RequestBody @Valid CartItemCreateRequest request) {
        final CartItemResponse response = cartItemService.add(loginCustomer, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> getCartItems(@AuthenticationPrincipal LoginCustomer loginCustomer) {
        return ResponseEntity.ok().body(cartItemService.findByCustomer(loginCustomer));
    }

    @PutMapping("/{cartItemId}")
    public ResponseEntity<CartItemResponse> updateQuantity(@AuthenticationPrincipal LoginCustomer loginCustomer,
                                                           @PathVariable Long cartItemId,
                                                           @RequestBody @Valid CartItemUpdateRequest request) {
        return ResponseEntity.ok(cartItemService.updateQuantity(loginCustomer, cartItemId, request));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll(@AuthenticationPrincipal LoginCustomer loginCustomer) {
        cartItemService.deleteAll(loginCustomer);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> delete(@PathVariable final Long cartItemId,
                                       @AuthenticationPrincipal LoginCustomer loginCustomer) {
        cartItemService.delete(loginCustomer, cartItemId);
        return ResponseEntity.noContent().build();
    }
}
