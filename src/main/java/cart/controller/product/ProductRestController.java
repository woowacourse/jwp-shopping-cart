package cart.controller.product;

import cart.controller.product.dto.ProductRequest;
import cart.service.product.ProductService;
import cart.service.product.dto.ProductResponse;
import cart.service.product.dto.ProductServiceRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/products")
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid ProductRequest productRequest) {
        ProductServiceRequest productServiceRequest = new ProductServiceRequest(productRequest.getName(), productRequest.getImageUrl(), productRequest.getPrice());
        Long productId = productService.create(productServiceRequest);
        return ResponseEntity.created(URI.create("/products/" + productId)).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductWebResponse> update(@RequestBody ProductRequest productRequest, @PathVariable Long id) {
        ProductServiceRequest productServiceRequest = new ProductServiceRequest(productRequest.getName(), productRequest.getImageUrl(), productRequest.getPrice());
        ProductResponse updated = productService.update(productServiceRequest, id);
        ProductWebResponse updateResponse = new ProductWebResponse(updated.getId(), updated.getName(), updated.getImageUrl(), updated.getPrice());
        return ResponseEntity.ok(updateResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
