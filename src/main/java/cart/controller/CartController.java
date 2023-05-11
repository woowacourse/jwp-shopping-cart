package cart.controller;

import cart.auth.AuthenticationPrincipal;
import cart.entity.CartItemEntity;
import cart.entity.ProductEntity;
import cart.service.CartItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart/item")
public class CartController {

    private final CartItemService cartItemService;

    public CartController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @GetMapping
    public ResponseEntity<List<ProductEntity>> getCartItem(Model model, @AuthenticationPrincipal int memberId) {
        List<ProductEntity> cartItems = cartItemService.getCartItems(memberId);

        model.addAttribute("cartItems", cartItems);
        return ResponseEntity.ok().body(cartItems);
    }

    @PostMapping
    public ResponseEntity<Void> insertCartItem(@RequestParam int productId, @AuthenticationPrincipal int memberId) {
        cartItemService.addCartItem(new CartItemEntity(memberId, productId));

        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCartItem(@RequestParam int cartId) {
        cartItemService.deleteCartItem(cartId);
        return ResponseEntity.ok().build();
    }

}
