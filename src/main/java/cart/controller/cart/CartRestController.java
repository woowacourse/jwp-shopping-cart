package cart.controller.cart;

import cart.common.argumentresolver.Member;
import cart.controller.member.dto.MemberRequest;
import cart.service.cart.CartService;
import cart.service.cart.dto.CartServiceRequest;
import cart.service.cart.dto.ProductResponse;
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
        CartServiceRequest cartServiceRequest = new CartServiceRequest(memberRequest.getEmail(), productId);
        Long cartId = cartService.createCartItem(cartServiceRequest);
        return ResponseEntity.created(URI.create("carts/" + cartId)).build();
    }

    @GetMapping("/items")
    public ResponseEntity<List<ProductResponse>> showCartItems(@Member MemberRequest memberRequest) {
        CartServiceRequest cartServiceRequest = new CartServiceRequest(memberRequest.getEmail());
        List<ProductResponse> cartItems = cartService.findProductsByUserIdOnCart(cartServiceRequest);
        return ResponseEntity.ok(cartItems);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteCartItem(@Member MemberRequest memberRequest, @PathVariable long productId) {
        CartServiceRequest cartServiceRequest = new CartServiceRequest(memberRequest.getEmail(), productId);
        cartService.deleteCartItem(cartServiceRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
