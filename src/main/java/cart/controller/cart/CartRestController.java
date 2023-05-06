package cart.controller.cart;

import cart.common.argumentresolver.Member;
import cart.service.cart.CartService;
import cart.service.cart.dto.ProductResponse;
import cart.service.member.dto.MemberRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartRestController {

    private final CartService cartService;

    public CartRestController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/{productId}")
    public ResponseEntity<Void> createCart(@Member MemberRequest memberRequest, @PathVariable Long productId) {
        Long cartId = cartService.createCartItem(memberRequest.getEmail(), productId);
        return ResponseEntity.created(URI.create("carts/" + cartId)).build();
    }

    @GetMapping("/items")
    public ResponseEntity<List<ProductResponse>> showCartItems(@Member MemberRequest memberRequest) {
        List<ProductResponse> cartItems = cartService.findProductsByUserIdOnCart(memberRequest.getEmail());
        return ResponseEntity.ok(cartItems);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteCartItem(@Member MemberRequest memberRequest, @PathVariable int productId) {
        cartService.deleteCartItem(memberRequest.getEmail(), (long) productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
