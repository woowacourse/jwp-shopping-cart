package woowacourse.shoppingcart.ui;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.domain.user.Customer;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.dto.request.UpdateCartItemQuantityRequest;
import woowacourse.shoppingcart.dto.response.CartDto;

@Validated
@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<CartDto> getAllCartItems(Customer authCustomer) {
        CartDto responseBody = new CartDto(cartService.findCart(authCustomer));
        return ResponseEntity.ok().body(responseBody);
    }

    @DeleteMapping
    public ResponseEntity<Void> emptyCart(Customer authCustomer) {
        cartService.clearCart(authCustomer);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{productId}")
    public ResponseEntity<Void> registerCartItem(Customer authCustomer,
                                                 @PathVariable Long productId) {
        cartService.registerNewCartItem(authCustomer, productId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{productId}/quantity")
    public ResponseEntity<Void> updateCartItemQuantity(Customer authCustomer,
                                                       @PathVariable Long productId,
                                                       @RequestBody UpdateCartItemQuantityRequest requestBody) {
        cartService.updateCartItem(authCustomer, productId, requestBody.getQuantity());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/products")
    public ResponseEntity<Void> removeCartItems(Customer authCustomer,
                                                @NotEmpty(message = "제거 대상 정보 필요") @RequestParam("id") List<Long> productIds) {
        cartService.removeCartItems(authCustomer, productIds);
        return ResponseEntity.noContent().build();
    }
}
