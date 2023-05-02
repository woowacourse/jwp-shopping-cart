package cart.controller;

import cart.auth.AuthSubject;
import cart.cart.dto.CartResponse;
import cart.cart.service.CartService;
import cart.member.dto.MemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {
    private final CartService cartService;
    
    @PostMapping("{productId}")
    public ResponseEntity<CartResponse> save(@PathVariable final Long productId, @AuthSubject MemberRequest memberRequest) {
        final Long cartId = cartService.addCart(productId, memberRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CartResponse(cartId, memberRequest.getId(), productId));
    }
}
