package cart.controller;

import cart.dto.ProductIdRequest;
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

    @PostMapping
    public ResponseEntity<CartItem> addItem(@RequestHeader("Authorization") String authorization, @RequestBody ProductIdRequest productId) {
        if (authorization.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        final CartItem addedItem = cartService.addItem(authorization, productId.getProductId());
        return ResponseEntity.ok().body(addedItem);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> cart(@RequestHeader("Authorization") String authorization) {
        if (authorization.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        final List<Product> products = cartService.findCartItems(authorization);
        return ResponseEntity.ok().body(products);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestHeader("Authorization") String authorization, @RequestParam("memberId") Long memberId) {
        return ResponseEntity.noContent().build();
    }
}
