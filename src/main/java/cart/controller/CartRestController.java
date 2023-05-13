package cart.controller;

import cart.authentication.MemberInfo;
import cart.authentication.RequestMember;
import cart.dto.cartitem.CartItemRequest;
import cart.dto.cartitem.CartItemResponse;
import cart.service.cart.CartService;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartRestController {

    private final CartService cartService;

    public CartRestController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/cart-items")
    public ResponseEntity<Long> saveCart(@Valid @RequestMember final MemberInfo memberInfo, @RequestBody final CartItemRequest cartItemRequest) {
        Long cartId = cartService.saveCart(memberInfo, cartItemRequest);
        return ResponseEntity.created(URI.create("/carts/" + cartId)).build();
    }

    @GetMapping("/cart-items")
    public ResponseEntity<List<CartItemResponse>> findCart(@Valid @RequestMember final MemberInfo memberInfo) {
        List<CartItemResponse> cartItemResponses = cartService.findCartByMember(memberInfo);
        return ResponseEntity.ok(cartItemResponses);
    }

    @DeleteMapping("/cart-items/{cartItemId}")
    public ResponseEntity<Void> deleteCart(@Valid @RequestMember final MemberInfo memberInfo, @PathVariable final Long cartItemId) {
        cartService.deleteCartItem(memberInfo, cartItemId);
        return ResponseEntity.noContent().build();
    }
}
