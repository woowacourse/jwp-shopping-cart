package cart.controller;

import cart.dto.ProductDto;
import cart.dto.request.ProductCreateRequest;
import cart.dto.request.ProductUpdateRequest;
import cart.service.ProductService;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductApiController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody @Valid ProductCreateRequest request) {
        ProductDto productDto = productService.createProduct(request.getName(), request.getPrice(),
                request.getImageUrl());
        return ResponseEntity
                .created(URI.create("/products/" + productDto.getId()))
                .body(productDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity
                .ok()
                .build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id,
                                                    @RequestBody @Valid ProductUpdateRequest request) {
        ProductDto productDto = productService.updateProductById(id, request.getName(), request.getPrice(),
                request.getImageUrl());
        return ResponseEntity
                .ok()
                .body(productDto);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> findAllProducts() {
        List<ProductDto> allProducts = productService.findAllProducts();
        return ResponseEntity
                .ok()
                .body(allProducts);
    }
}
