package cart.controller;

import cart.entity.item.CartItem;
import cart.entity.product.Product;
import cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> findItems(@RequestHeader("Authorization") String authorization) {
        if (authorization.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        final List<Product> products = cartService.findCartItems(authorization);
        return ResponseEntity.ok().body(products);
    }

    @PostMapping("/{productId}")
    public ResponseEntity<CartItem> addItem(@PathVariable("productId") long productId, @RequestHeader("Authorization") String authorization) {
        if (authorization.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        final CartItem addedItem = cartService.addItem(authorization, productId);
        return ResponseEntity.ok().body(addedItem);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteItem(@PathVariable("productId") long productId, @RequestHeader("Authorization") String authorization) {
        if (authorization.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        cartService.deleteItem(authorization, productId);
        return ResponseEntity.noContent().build();
    }
}
