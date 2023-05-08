package cart.controller;

import cart.domain.cartitem.CartItem;
import cart.domain.cartitem.CartItemService;
import cart.dto.CartItemDto;
import cart.infratstructure.AuthenticationPrincipal;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cartitems")
public class CartItemController {

    private final CartItemService cartItemService;

    public CartItemController(final CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @GetMapping
    public ResponseEntity<List<CartItemDto>> get(@AuthenticationPrincipal Long loginMemberId) {
        return ResponseEntity.ok(cartItemService.findAllByMemberId(loginMemberId));
    }

    @PostMapping("/{productId}")
    public ResponseEntity<Void> create(@PathVariable Long productId, @AuthenticationPrincipal Long loginMemberId) {
        cartItemService.add(new CartItem(loginMemberId, productId));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Long cartItemId,
                                       @AuthenticationPrincipal Long loginMemberId) {
        cartItemService.deleteById(loginMemberId, cartItemId);
        return ResponseEntity.noContent().build();
    }
}
