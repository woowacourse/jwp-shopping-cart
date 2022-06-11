package woowacourse.shoppingcart.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.auth.support.LoginCustomer;
import woowacourse.shoppingcart.dto.Request;
import woowacourse.shoppingcart.dto.request.AddCartItemRequestDto;
import woowacourse.shoppingcart.dto.request.UpdateCartItemCountItemRequest;
import woowacourse.shoppingcart.dto.response.CartItemResponseDto;
import woowacourse.shoppingcart.service.CartService;

import java.net.URI;
import java.util.List;

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

        cartService.addCart(addCartItemRequestDto, customerId);
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

    @PatchMapping
    public ResponseEntity<Void> updateCartItem(@PathVariable final Long customerId,
                                               @RequestParam final Long productId,
                                               @RequestBody final UpdateCartItemCountItemRequest updateCartItemCountItemRequest,
                                               @AuthenticationPrincipal LoginCustomer loginCustomer) {
        authService.checkAuthorization(customerId, loginCustomer.getEmail());

        cartService.updateCart(customerId, productId, updateCartItemCountItemRequest);
        return ResponseEntity.ok().build();
    }
}
