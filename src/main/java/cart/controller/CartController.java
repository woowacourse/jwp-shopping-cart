package cart.controller;

import cart.dto.CartProductResponse;
import cart.dto.ProductId;
import cart.mvcconfig.AuthenticationPrincipal;
import cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    public CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartProductResponse>> getCartView(
            @AuthenticationPrincipal String email) {
        List<CartProductResponse> products = cartService.readCart(email);

        return ResponseEntity.ok(products);
    }

    @PostMapping("/product")
    public ResponseEntity<Void> addCartItem(
            @AuthenticationPrincipal String email, @RequestBody ProductId productId) {

        final long cartId = cartService.addCartItem(email, productId.getId());

        return ResponseEntity.created(URI.create("/carts/product/" + cartId)).build();
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long id) {
        cartService.deleteCartItem(id);

        return ResponseEntity.noContent().build();
    }
}
