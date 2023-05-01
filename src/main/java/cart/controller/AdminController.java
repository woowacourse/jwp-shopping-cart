package cart.controller;

import cart.dto.ProductRequest;
import cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final CartService cartService;

    public AdminController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/product")
    public ResponseEntity<Void> createProduct(@RequestBody @Valid final ProductRequest productRequest) {
        final long id = cartService.create(productRequest);
        return ResponseEntity.created(URI.create("/admin/product/" + id)).build();
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable Long id, @RequestBody @Valid final ProductRequest productRequest) {
        cartService.update(id, productRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        cartService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
