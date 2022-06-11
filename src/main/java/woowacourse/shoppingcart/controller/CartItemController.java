package woowacourse.shoppingcart.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.auth.support.LoginCustomer;
import woowacourse.shoppingcart.dto.Request;
import woowacourse.shoppingcart.dto.request.AddCartItemRequestDto;
import woowacourse.shoppingcart.dto.request.UpdateCartItemCountItemRequest;
import woowacourse.shoppingcart.dto.response.CartItemResponseDto;
import woowacourse.shoppingcart.service.CartItemService;

import java.util.List;

@RestController
@RequestMapping("/api/customers/{customerId}/carts")
public class CartItemController {

    private final CartItemService cartItemService;
    private final AuthService authService;

    public CartItemController(final CartItemService cartItemService, final AuthService authService) {
        this.cartItemService = cartItemService;
        this.authService = authService;
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponseDto>> getCartItems(@PathVariable final Long customerId,
                                                                  @AuthenticationPrincipal LoginCustomer loginCustomer) {
        authService.checkAuthorization(customerId, loginCustomer.getEmail());
        return ResponseEntity.ok().body(cartItemService.findCartItemsByCustomerId(customerId));
    }

    @PostMapping
    public ResponseEntity<Void> addCartItem(@Validated @RequestBody final AddCartItemRequestDto addCartItemRequestDto,
                                            @PathVariable final Long customerId,
                                            @AuthenticationPrincipal LoginCustomer loginCustomer) {
        authService.checkAuthorization(customerId, loginCustomer.getEmail());

        cartItemService.addCartItem(addCartItemRequestDto, customerId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCartItem(@PathVariable final Long customerId,
                                               @RequestParam final Long productId,
                                               @AuthenticationPrincipal LoginCustomer loginCustomer) {
        authService.checkAuthorization(customerId, loginCustomer.getEmail());

        cartItemService.deleteCartItem(customerId, productId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping
    public ResponseEntity<Void> updateCartItem(@PathVariable final Long customerId,
                                               @RequestParam final Long productId,
                                               @RequestBody @Validated final UpdateCartItemCountItemRequest updateCartItemCountItemRequest,
                                               @AuthenticationPrincipal LoginCustomer loginCustomer) {
        authService.checkAuthorization(customerId, loginCustomer.getEmail());

        cartItemService.updateCartItem(customerId, productId, updateCartItemCountItemRequest);
        return ResponseEntity.ok().build();
    }
}
