package cart.controller;

import javax.validation.Valid;

import cart.domain.product.Product;
import cart.dto.ProductRequest;
import cart.dto.ProductResponse;
import cart.service.ProductUpdateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductUpdateController {

    private final ProductUpdateService productUpdateService;

    public ProductUpdateController(final ProductUpdateService productUpdateService) {
        this.productUpdateService = productUpdateService;
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable final long id,
                                                         @RequestBody @Valid final ProductRequest productRequest) {
        final Product product = productUpdateService.update(id, productRequest.getName(), productRequest.getImage(),
                productRequest.getPrice());
        final ProductResponse productResponse = ProductResponse.from(product);

        return ResponseEntity.ok(productResponse);
    }
}
