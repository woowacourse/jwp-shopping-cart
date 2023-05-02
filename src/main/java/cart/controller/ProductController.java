package cart.controller;

import cart.dto.ProductResponse;
import cart.service.ProductService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponse>> getAllProduct() {
        List<ProductResponse> products = productService.findAll();
        return ResponseEntity.ok(products);
    }
}
