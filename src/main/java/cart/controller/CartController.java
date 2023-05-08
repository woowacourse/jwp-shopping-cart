package cart.controller;

import cart.auth.Authentication;
import cart.controller.dto.CartResponse;
import cart.controller.dto.ItemResponse;
import cart.service.CartService;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/carts")
@RestController
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<ItemResponse>> findAllCarts(@Authentication Long memberId) {
        List<ItemResponse> response = cartService.findAllByMemberId(memberId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{itemId}")
    public ResponseEntity<CartResponse> addItem(@PathVariable Long itemId,
                                                @Authentication Long memberId) {

        CartResponse cartResponse = cartService.save(memberId, itemId);
        return ResponseEntity.created(URI.create("/carts/" + cartResponse.getCartId()))
                .body(cartResponse);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long itemId, @Authentication Long memberId) {
        cartService.delete(memberId, itemId);
        return ResponseEntity.noContent().build();
    }
}
