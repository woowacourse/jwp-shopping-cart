package cart.controller.product;

import cart.controller.product.dto.ProductCreateRequest;
import cart.controller.product.dto.ProductWebResponse;
import cart.service.product.ProductService;
import cart.service.product.dto.ProductInsertRequest;
import cart.service.product.dto.ProductResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Void> create(@RequestBody @Valid ProductCreateRequest productRequest) {
        ProductInsertRequest productServiceRequest = new ProductInsertRequest(productRequest.getName(), productRequest.getImageUrl(), productRequest.getPrice());
        long productId = productService.create(productServiceRequest);
        return ResponseEntity.created(URI.create("/products/" + productId)).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductWebResponse> update(@RequestBody ProductCreateRequest productRequest, @PathVariable Long id) {
        ProductInsertRequest productServiceRequest = new ProductInsertRequest(productRequest.getName(), productRequest.getImageUrl(), productRequest.getPrice());
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
