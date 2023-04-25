package cart.controller;

import cart.dto.ProductCreateRequestDto;
import cart.dto.ProductsResponseDto;
import cart.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<ProductsResponseDto> findProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    @PostMapping
    public ResponseEntity<Void> createProduct(@RequestBody ProductCreateRequestDto productCreateRequestDto) {
        productService.createProduct(productCreateRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }
}
