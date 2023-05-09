package cart.product.controller;

import cart.product.dto.ProductCreationRequest;
import cart.product.dto.ProductModificationRequest;
import cart.product.dto.ProductResponse;
import cart.product.mapper.ProductResponseMapper;
import cart.product.mapper.ProductMapper;
import cart.product.service.ProductService;
import lombok.RequiredArgsConstructor;
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

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/products")
@RequiredArgsConstructor
@RestController
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Void> postProducts(@Valid @RequestBody ProductCreationRequest request) {
        productService.add(ProductMapper.from(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts() {
        final List<ProductResponse> productResponses = ProductResponseMapper.from(productService.findAll());
        return ResponseEntity.ok(productResponses);
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<Void> putProducts(@PathVariable Long productId,
                                            @Valid @RequestBody ProductModificationRequest request) {
        productService.updateById(productId, ProductMapper.from(request));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProducts(@PathVariable Long productId) {
        productService.deleteById(productId);
        return ResponseEntity.noContent().build();
    }
}
