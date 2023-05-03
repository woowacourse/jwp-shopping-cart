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

    @PostMapping("/{productId}")
    public ResponseEntity<CartItem> addItem(@PathVariable("productId") long productId, @RequestHeader("Authorization") String authorization) {
        if (authorization.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        final CartItem addedItem = cartService.addItem(authorization, productId);
        return ResponseEntity.ok().body(addedItem);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> cart(@RequestHeader("Authorization") String authorization) {
        if (authorization.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        final List<Product> products = cartService.findCartItems(authorization);
        System.out.println(products);
        return ResponseEntity.ok().body(products);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> delete(@PathVariable("productId") long productId, @RequestHeader("Authorization") String authorization) {
        if (authorization.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        cartService.deleteItem(authorization, productId);
        return ResponseEntity.noContent().build();
    }
}
