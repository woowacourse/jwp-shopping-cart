package cart.controller;

import cart.dto.ProductRequest;
import cart.entity.Product;
import cart.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/products")
public class ProductsController {

    private final ProductService productService;

    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        Product product = new Product(productRequest.getName(), productRequest.getImgUrl(), productRequest.getPrice());
        Product createdProduct = productService.createProduct(product);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/")
                .build()
                .toUri();
        return ResponseEntity.created(location).body(createdProduct);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable long id, @Valid @RequestBody ProductRequest productRequest) {
        Product product = new Product(id, productRequest.getName(), productRequest.getImgUrl(), productRequest.getPrice());
        productService.updateProduct(product);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long id) {
        productService.deleteProductBy(id);
        return ResponseEntity.ok().build();
    }
}
