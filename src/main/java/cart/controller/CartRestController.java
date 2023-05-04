package cart.controller;

import cart.authentication.RequestMember;
import cart.dto.cart.CartRequest;
import cart.dto.member.MemberRequest;
import cart.service.cart.CartService;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartRestController {

    private final CartService cartService;

    public CartRestController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/carts")
    public ResponseEntity<Long> saveCart(@RequestMember final MemberRequest memberRequest, @RequestBody final CartRequest cartRequest) {
        Long cartId = cartService.saveCart(memberRequest, cartRequest);
        return ResponseEntity.created(URI.create("/carts/" + cartId)).build();
    }
}
