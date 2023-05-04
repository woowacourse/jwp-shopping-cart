package cart.controller;

import cart.controller.dto.ProductResponse;
import cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cart/products")
public class CartController {

    private final CartService cartService;

    public CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts(final HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        List<ProductResponse> productByEmail = cartService.findByEmail(email);
        return ResponseEntity
                .ok()
                .body(productByEmail);
    }

    @PostMapping("/{productId}")
    public ResponseEntity<Void> addProduct(@PathVariable final Long productId, final HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        cartService.save(email, productId);

        return ResponseEntity
                .created(URI.create("/cart/products"))
                .build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable final Long productId, final HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        cartService.delete(email, productId);
        return ResponseEntity
                .noContent()
                .build();
    }
}
