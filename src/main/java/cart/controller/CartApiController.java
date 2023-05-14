package cart.controller;

import cart.auth.AuthenticationPrincipal;
import cart.controller.dto.request.cart.CartInsertRequest;
import cart.controller.dto.response.CartResponse;
import cart.service.CartService;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartApiController {

    private final CartService cartService;

    public CartApiController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/carts")
    public ResponseEntity<List<CartResponse>> getCarts(@AuthenticationPrincipal Integer memberId) {
        return ResponseEntity.ok().body(cartService.findAll(memberId));
    }

    @PostMapping("/carts")
    public ResponseEntity<Void> addCart(@AuthenticationPrincipal Integer memberId,
                                        @RequestBody CartInsertRequest cartInsertRequest) {
        cartService.insertCart(cartInsertRequest.getProductId(), memberId);
        return ResponseEntity.created(URI.create("/cart")).build();
    }

    @DeleteMapping("/carts/{cartId}")
    public ResponseEntity<Void> deleteCart(@PathVariable int cartId, @AuthenticationPrincipal Integer memberId) {
        cartService.deleteCart(cartId, memberId);
        return ResponseEntity.noContent().build();
    }

}
