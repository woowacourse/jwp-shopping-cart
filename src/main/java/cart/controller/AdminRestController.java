package cart.controller;

import cart.controller.dto.ProductDto;
import cart.service.ProductService;
import java.net.URI;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminRestController {

    private final ProductService productService;

    public AdminRestController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> addProduct(@RequestBody @Valid final ProductDto productDto) {
        final long productId = productService.save(productDto);
        return ResponseEntity.created(URI.create("/" + productId)).build();
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Void> updateProduct(
        @PathVariable final Long productId,
        @RequestBody @Valid final ProductDto productDto) {
        productService.update(productId, productDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable final Long productId) {
        productService.delete(productId);
        return ResponseEntity.noContent().build();
    }
}
