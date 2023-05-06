package shoppingbasket.product.controller;

import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shoppingbasket.product.dto.ProductInsertRequestDto;
import shoppingbasket.product.dto.ProductUpdateRequestDto;
import shoppingbasket.product.entity.ProductEntity;
import shoppingbasket.product.service.ProductService;

@RestController
public class ProductApiController {

    private final ProductService productService;

    public ProductApiController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    public ResponseEntity<ProductEntity> insertProduct(@RequestBody @Valid ProductInsertRequestDto product) {
        final ProductEntity savedProduct = productService.addProduct(product);
        final int savedId = savedProduct.getId();

        return ResponseEntity.created(URI.create("/products/" + savedId))
                .body(savedProduct);
    }

    @GetMapping("/products/{id}")
    public ProductEntity findProductById(@PathVariable int id) {
        return productService.findProductById(id);
    }

    @GetMapping("/products")
    public List<ProductEntity> getProducts() {
        return productService.getProducts();
    }

    @PutMapping("/products")
    public ResponseEntity<ProductEntity> updateProduct(@RequestBody @Valid ProductUpdateRequestDto product) {
        final ProductEntity updatedProduct = productService.updateProduct(product);

        if (updatedProduct == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        final int deletedRowCount = productService.deleteProduct(id);

        if (deletedRowCount == 0) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }

}
