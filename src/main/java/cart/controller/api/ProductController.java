package cart.controller.api;

import cart.dto.ProductCreationRequest;
import cart.dto.ProductModificationRequest;
import cart.dto.ProductResponse;
import cart.mapper.ProductMapper;
import cart.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/products")
@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Void> postProducts(@RequestBody ProductCreationRequest request) {
        productService.add(ProductMapper.from(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<Void> putProducts(@PathVariable Long productId,
                                            @RequestBody ProductModificationRequest request) {
        productService.updateById(productId, ProductMapper.from(request));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProducts(@PathVariable Long productId) {
        productService.deleteById(productId);
        return ResponseEntity.noContent().build();
    }
}
