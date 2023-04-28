package cart.controller.admin;

import cart.controller.dto.ProductDto;
import cart.service.ShoppingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/admin/products")
public class AdminProductController {

    private final ShoppingService shoppingService;

    public AdminProductController(final ShoppingService shoppingService) {
        this.shoppingService = shoppingService;
    }

    @PostMapping
    public ResponseEntity<Void> addProduct(@RequestBody @Valid final ProductDto productDto) {
        final Long productId = shoppingService.save(productDto);
        return ResponseEntity.created(URI.create("/admin/" + productId)).build();
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Void> updateProduct(@PathVariable final Long productId,
                                              @RequestBody @Valid final ProductDto productDto) {
        shoppingService.update(productId, productDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable final Long productId) {
        shoppingService.delete(productId);
        return ResponseEntity.noContent().build();
    }
}
