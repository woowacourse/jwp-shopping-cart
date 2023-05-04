package cart.controller;

import cart.authentication.RequestMember;
import cart.dto.cart.CartRequest;
import cart.dto.cartitem.CartItemResponse;
import cart.dto.member.MemberRequest;
import cart.service.cart.CartService;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<Long> saveCart(@RequestMember final MemberRequest memberRequest, @RequestBody final CartRequest cartRequest) {
        Long cartId = cartService.saveCart(memberRequest, cartRequest);
        return ResponseEntity.created(URI.create("/carts/" + cartId)).build();
    }

    @GetMapping("/cart-items")
    public ResponseEntity<List<CartItemResponse>> findCart(@RequestMember final MemberRequest memberRequest) {
        List<CartItemResponse> cartItemResponses = cartService.findCartByMember(memberRequest);
        return ResponseEntity.ok(cartItemResponses);
    }
}
