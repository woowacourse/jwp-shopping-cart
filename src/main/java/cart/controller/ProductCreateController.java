package cart.controller;

import cart.domain.Product;
import cart.dto.ProductRequest;
import cart.dto.ProductResponse;
import cart.service.ProductCommandService;
import java.net.URI;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductCreateController {

    private final ProductCommandService productCommandService;

    public ProductCreateController(final ProductCommandService productCommandService) {
        this.productCommandService = productCommandService;
    }

    @PostMapping("/products")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid final ProductRequest productRequest) {
        final Product product = productCommandService.create(productRequest.getName(), productRequest.getImage(),
                productRequest.getPrice());
        final ProductResponse productResponse = ProductResponse.from(product);

        return ResponseEntity.created(URI.create("/products/" + product.getProductId().getValue()))
                .body(productResponse);
    }
}
