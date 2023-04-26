package cart.controller;

import cart.dto.AddProductRequest;
import cart.dto.UpdateProductRequest;
import cart.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@Valid @RequestBody AddProductRequest addProductRequest) {
        productService.addProduct(addProductRequest);
        return ResponseEntity.ok().build(); // TODO: 2023/04/26 created 변경
    }

    @PutMapping
    public ResponseEntity<?> updateProduct(@Valid @RequestBody UpdateProductRequest updateProductRequest) {
        productService.updateProduct(updateProductRequest);
        return ResponseEntity.ok().build();
    }
}
