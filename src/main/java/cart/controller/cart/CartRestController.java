package cart.controller.cart;

import cart.common.argumentresolver.Member;
import cart.controller.member.dto.MemberRequest;
import cart.service.cart.CartService;
import cart.service.cart.dto.DeleteCartITemRequest;
import cart.service.cart.dto.InsertCartItemRequest;
import cart.service.cart.dto.ProductResponse;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/carts")
public class CartRestController {

    private final CartService cartService;

    public CartRestController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/{productId}")
    public ResponseEntity<Void> createCart(@Member MemberRequest memberRequest, @PathVariable Long productId) {
        InsertCartItemRequest InsertCartItemRequest = new InsertCartItemRequest(memberRequest.getEmail(), productId);
        Long cartId = cartService.createCartItem(InsertCartItemRequest);
        return ResponseEntity.created(URI.create("carts/" + cartId)).build();
    }

    @GetMapping("/items")
    public ResponseEntity<List<ProductResponse>> showCartItems(@Member MemberRequest memberRequest) {
        InsertCartItemRequest InsertCartItemRequest = new InsertCartItemRequest(memberRequest.getEmail());
        List<ProductResponse> cartItems = cartService.findProductsByUserIdOnCart(InsertCartItemRequest);
        return ResponseEntity.ok(cartItems);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteCartItem(@Member MemberRequest memberRequest, @PathVariable long productId) {
        DeleteCartITemRequest deleteCartITemRequest = new DeleteCartITemRequest(memberRequest.getEmail(), productId);
        cartService.deleteCartItem(deleteCartITemRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
