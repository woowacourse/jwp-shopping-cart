package cart.controller;

import cart.domain.Product;
import cart.dto.ProductRequest;
import cart.dto.ProductsResponse;
import cart.service.ProductService;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Void> createProduct(@RequestBody @Valid ProductRequest request) {
        productService.save(request.getName(), request.getPrice(), request.getImageUrl());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<ProductsResponse> readProducts() {
        List<Product> products = productService.findAll();
        return ResponseEntity.ok(ProductsResponse.of(products));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(
            @PathVariable @NotNull Long id,
            @RequestBody @Valid ProductRequest request) {
        productService.update(id, request.getName(), request.getPrice(), request.getImageUrl());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable @NotNull Long id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }
}
