package woowacourse.shoppingcart.ui;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.shoppingcart.application.CartItemService;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.Request;
import woowacourse.shoppingcart.dto.cartitem.CartItemCreateRequest;

@RestController
@RequestMapping("/api/customers/{customerId}/carts")
@RequiredArgsConstructor
public class CartItemController {

    private final CartItemService cartItemService;

    @PostMapping
    public ResponseEntity<Void> addCartItem(@PathVariable final long customerId,
            @RequestBody final CartItemCreateRequest requestBody
    ) {
        final Long cartId = cartItemService.addCartItem(requestBody.getProductId(), customerId, requestBody.getCount());
        final URI responseLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{cartId}")
                .buildAndExpand(cartId)
                .toUri();
        return ResponseEntity.created(responseLocation).build();
    }

    @GetMapping
    public ResponseEntity<List<CartItem>> getCartItems(@PathVariable final long customerId) {
        return ResponseEntity.ok().body(cartItemService.findCartsByCustomerId(customerId));
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable final long customerId,
                                               @PathVariable final long cartId) {
        cartItemService.deleteCart(customerId, cartId);
        return ResponseEntity.noContent().build();
    }
}
