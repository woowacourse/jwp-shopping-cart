package cart.controller;

import cart.common.auth.MemberEmail;
import cart.service.CartService;
import cart.service.dto.CartResponse;
import cart.service.dto.ProductResponse;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartRestController {

    private final CartService cartService;

    public CartRestController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/{productId}")
    public ResponseEntity<Void> addCart(@MemberEmail String memberEmail,
                                        @PathVariable Long productId) {
        cartService.addCart(memberEmail, productId);
        return ResponseEntity.created(URI.create("/cart/me")).build();
    }

    @GetMapping("/me")
    public ResponseEntity<CartResponse> getCartByMember(@MemberEmail String memberEmail) {
        final List<ProductResponse> productResponses = cartService.getProductsByMemberEmail(memberEmail);
        final CartResponse cartResponse = new CartResponse(productResponses);
        return ResponseEntity.ok().body(cartResponse);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteCart(@MemberEmail String memberEmail,
                                           @PathVariable Long productId) {
        cartService.deleteCart(memberEmail, productId);
        return ResponseEntity.noContent().build();
    }
}
