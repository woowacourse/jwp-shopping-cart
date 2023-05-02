package cart.controller;

import cart.dto.ProductRequest;
import cart.entity.Product;
import cart.service.ProductCreateService;
import cart.service.ProductDeleteService;
import cart.service.ProductUpdateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/products")
public class ProductsController {

    private final ProductCreateService createService;
    private final ProductUpdateService updateService;
    private final ProductDeleteService deleteService;

    public ProductsController(
            final ProductCreateService createService,
            final ProductUpdateService updateService,
            final ProductDeleteService deleteService) {
        this.createService = createService;
        this.updateService = updateService;
        this.deleteService = deleteService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        Product product = new Product(productRequest.getName(), productRequest.getImgUrl(), productRequest.getPrice());
        Product createdProduct = createService.createProduct(product);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/" + createdProduct.getId())
                .build()
                .toUri();
        return ResponseEntity.created(location).body(createdProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable long id, @Valid @RequestBody ProductRequest productRequest) {
        Product product = new Product(id, productRequest.getName(), productRequest.getImgUrl(), productRequest.getPrice());
        updateService.updateProduct(product);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long id) {
        deleteService.deleteProductBy(id);
        return ResponseEntity.noContent().build();
    }
}
