package cart.controller;

import cart.dto.product.ProductCreateRequestDto;
import cart.dto.product.ProductEditRequestDto;
import cart.dto.product.ProductsResponseDto;
import cart.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<ProductsResponseDto> findProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    @PostMapping
    public ResponseEntity<Void> createProduct(@RequestBody @Valid final ProductCreateRequestDto productCreateRequestDto) {
        Long productId = productService.createProduct(productCreateRequestDto);

        return ResponseEntity.created(URI.create("/products/" + productId))
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editProduct(@PathVariable final Long id, @RequestBody @Valid final ProductEditRequestDto productEditRequestDto) {
        Long productId = productService.editProduct(id, productEditRequestDto);

        return ResponseEntity.status(HttpStatus.OK)
                .location(URI.create("/products/" + productId))
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable final Long id) {
        productService.deleteById(id);

        return ResponseEntity.ok()
                .build();
    }
}
