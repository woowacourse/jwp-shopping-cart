package cart.controller;

import cart.common.annotation.MemberEmail;
import cart.controller.dto.CartDto;
import cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

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
    public ResponseEntity<List<CartDto>> getCartByMember(@MemberEmail String memberEmail) {
        final List<CartDto> cartDtos = cartService.getProductsByMemberEmail(memberEmail);
        return ResponseEntity.ok().body(cartDtos);
    }

    @DeleteMapping("/{targetMemberId}/{productId}")
    public ResponseEntity<Void> deleteCart(@MemberEmail String memberEmail,
                                           @PathVariable Long targetMemberId,
                                           @PathVariable Long productId) {
        cartService.deleteCart(targetMemberId, memberEmail, productId);
        return ResponseEntity.noContent().build();
    }
}
