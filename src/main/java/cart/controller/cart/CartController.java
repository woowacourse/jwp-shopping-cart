package cart.controller.cart;

import cart.config.auth.Auth;
import cart.dto.ProductDto;
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

    @GetMapping("")
    public ResponseEntity<List<ProductDto>> findProduct(@Auth final String email) {
        List<ProductDto> products = cartService.findAllCartProductByEmail(email);
        return ResponseEntity.ok().body(products);
    }

    @PostMapping("/{product_id}")
    public ResponseEntity<Void> addProduct(@PathVariable("product_id") final Long productId, @Auth final String email) {
        final Long id = cartService.addProductInCart(productId, email);
        return ResponseEntity.created(URI.create("/carts/" + id)).build();
    }

    @DeleteMapping("/{product_id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("product_id") final Long productId, @Auth final String email) {
        cartService.deleteProductInCart(productId, email);
        return ResponseEntity.noContent().build();
    }
}
