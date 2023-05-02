package cart.product.controller;

import cart.product.dto.ProductInsertRequestDto;
import cart.product.dto.ProductResponseDto;
import cart.product.dto.ProductUpdateRequestDto;
import cart.product.dto.ProductUpdateResponseDto;
import cart.product.entity.ProductEntity;
import cart.product.service.ProductService;
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
    public List<ProductResponseDto> getProducts() {
        return productService.getProducts();
    }

    @PutMapping("/products")
    public ResponseEntity<ProductUpdateResponseDto> updateProduct(@RequestBody @Valid ProductUpdateRequestDto product) {
        final int updatedRowCount = productService.updateProduct(product);
        final ProductUpdateResponseDto productUpdateResponseDto = new ProductUpdateResponseDto(updatedRowCount);

        return ResponseEntity.ok(productUpdateResponseDto);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<ProductUpdateResponseDto> deleteProduct(@PathVariable int id) {
        final int deletedRowCount = productService.deleteProduct(id);
        final ProductUpdateResponseDto productUpdateResponseDto = new ProductUpdateResponseDto(deletedRowCount);

        return ResponseEntity.ok(productUpdateResponseDto);
    }

}
