package cart.controller;

import cart.domain.Product;
import cart.dto.ProductRequest;
import cart.dto.ProductResponse;
import cart.service.ProductCommandService;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductUpdateController {

    private final ProductCommandService productCommandService;

    public ProductUpdateController(final ProductCommandService productCommandService) {
        this.productCommandService = productCommandService;
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable final long id,
            @RequestBody @Valid final ProductRequest productRequest) {
        final Product product = productCommandService.update(id, productRequest.getName(), productRequest.getImage(),
                productRequest.getPrice());
        final ProductResponse productResponse = ProductResponse.from(product);

        return ResponseEntity.ok(productResponse);
    }
}
