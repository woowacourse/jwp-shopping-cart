package cart.controller.api;

import cart.auth.AuthPrincipal;
import cart.dto.MemberDto;
import cart.dto.response.CartResponse;
import cart.service.CartService;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/carts")
    public ResponseEntity<List<CartResponse>> getCarts(@AuthPrincipal final MemberDto memberDto) {
        final List<CartResponse> productResponses = cartService.selectCart(memberDto);
        return ResponseEntity.ok(productResponses);
    }

    @PostMapping("/carts/{productId}")
    public ResponseEntity<Void> addCart(@PathVariable("productId") final Long productId, @AuthPrincipal final MemberDto memberDto) {
        cartService.insert(productId, memberDto);
        return ResponseEntity.created(URI.create("/carts")).build();
    }

    @DeleteMapping("/carts/{productId}")
    public ResponseEntity<Void> removeCart(@PathVariable("productId") final Long productId, @AuthPrincipal final MemberDto memberDto) {
        cartService.delete(productId, memberDto);
        return ResponseEntity.accepted().build();
    }
}
