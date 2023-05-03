package cart.controller.api;

import cart.dto.ProductCreationRequest;
import cart.dto.ProductModificationRequest;
import cart.dto.ProductResponse;
import cart.mapper.ProductEntityMapper;
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

    private final ProductService managementService;

    public ProductController(final ProductService managementService) {
        this.managementService = managementService;
    }

    @PostMapping
    public ResponseEntity<Void> postProducts(@RequestBody ProductCreationRequest request) {
        managementService.add(ProductEntityMapper.from(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts() {
        return ResponseEntity.ok(managementService.findAll());
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<Void> putProducts(@PathVariable Long productId,
                                            @RequestBody ProductModificationRequest request) {
        managementService.updateById(productId, ProductEntityMapper.from(request));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProducts(@PathVariable Long productId) {
        managementService.deleteById(productId);
        return ResponseEntity.noContent().build();
    }
}
