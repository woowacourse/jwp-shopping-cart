package woowacourse.shoppingcart.controller;

import java.net.URI;
import java.util.List;
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
import woowacourse.auth.application.AuthService;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.auth.support.LoginCustomer;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.AddCartItemRequestDto;
import woowacourse.shoppingcart.dto.CartItemResponseDto;
import woowacourse.shoppingcart.dto.Request;
import woowacourse.shoppingcart.service.CartService;

@RestController
@RequestMapping("/api/customers/{customerId}/carts")
public class CartItemController {

    private final CartService cartService;
    private final AuthService authService;

    public CartItemController(final CartService cartService, final AuthService authService) {
        this.cartService = cartService;
        this.authService = authService;
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponseDto>> getCartItems(@PathVariable final Long customerId,
                                                                  @AuthenticationPrincipal LoginCustomer loginCustomer) {
        authService.checkAuthorization(customerId, loginCustomer.getEmail());
        return ResponseEntity.ok().body(cartService.findCartsByCustomerId(customerId));
    }

    @PostMapping
    public ResponseEntity<Void> addCartItem(@Validated(Request.id.class) @RequestBody final AddCartItemRequestDto addCartItemRequestDto,
                                            @PathVariable final Long customerId,
                                            @AuthenticationPrincipal LoginCustomer loginCustomer) {
        authService.checkAuthorization(customerId, loginCustomer.getEmail());

        final Long cartId = cartService.addCart(addCartItemRequestDto, customerId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCartItem(@PathVariable final Long customerId,
                                               @RequestParam final Long productId,
                                               @AuthenticationPrincipal LoginCustomer loginCustomer) {
        authService.checkAuthorization(customerId, loginCustomer.getEmail());

        cartService.deleteCart(customerId, productId);
        return ResponseEntity.noContent().build();
    }
}
