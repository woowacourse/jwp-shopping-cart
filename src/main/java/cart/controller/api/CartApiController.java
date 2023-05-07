package cart.controller.api;

import cart.config.AuthenticationPrincipal;
import cart.controller.dto.CartResponse;
import cart.controller.dto.MemberRequest;
import cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartApiController {

    private final CartService cartService;

    public CartApiController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartResponse>> cart(@AuthenticationPrincipal MemberRequest memberRequest) {
        List<CartResponse> carts = cartService.getCartProductByMember(memberRequest);
        return ResponseEntity.ok(carts);
    }

    @PostMapping("{productId}")
    public ResponseEntity<Long> addCart(
            @PathVariable Long productId,
            @AuthenticationPrincipal MemberRequest memberRequest
    ) {
        Long id = cartService.addCart(productId, memberRequest);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("{cartId}")
    public void deleteCart(
            @PathVariable Long cartId
    ) {
        cartService.deleteCart(cartId);
    }
}
