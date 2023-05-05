package cart.controller;

import cart.authentication.RequestMember;
import cart.dto.cartitem.CartItemRequest;
import cart.dto.cartitem.CartItemResponse;
import cart.dto.member.MemberRequest;
import cart.service.cart.CartService;
import java.net.URI;
import java.util.List;
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
    public ResponseEntity<Long> saveCart(@RequestMember final MemberRequest memberRequest, @RequestBody final CartItemRequest cartItemRequest) {
        Long cartId = cartService.saveCart(memberRequest, cartItemRequest);
        return ResponseEntity.created(URI.create("/carts/" + cartId)).build();
    }

    @GetMapping("/cart-items")
    public ResponseEntity<List<CartItemResponse>> findCart(@RequestMember final MemberRequest memberRequest) {
        List<CartItemResponse> cartItemResponses = cartService.findCartByMember(memberRequest);
        return ResponseEntity.ok(cartItemResponses);
    }

    @DeleteMapping("/cart-items/{productId}")
    public ResponseEntity<Void> deleteCart(@RequestMember final MemberRequest memberRequest, @PathVariable final Long productId) {
        cartService.deleteCartItem(memberRequest, productId);
        return ResponseEntity.noContent().build();
    }
}
