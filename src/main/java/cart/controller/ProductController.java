package cart.controller;

import cart.dto.CreateProductRequest;
import cart.dto.UpdateProductRequest;
import cart.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Void> createProduct(@RequestBody @Valid CreateProductRequest request) {
        productService.save(request.getName(), request.getPrice(), request.getImage());
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateProduct(@RequestBody @Valid UpdateProductRequest request) {
        productService.update(request.getId(), request.getName(), request.getPrice(), request.getImage());
        return ResponseEntity.ok().build();
    }
}
