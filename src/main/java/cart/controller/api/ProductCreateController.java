package cart.controller.api;

import javax.validation.Valid;
import java.net.URI;

import cart.controller.dto.ProductRequest;
import cart.controller.dto.ProductResponse;
import cart.domain.product.Product;
import cart.service.ProductCreateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductCreateController {

    private final ProductCreateService productCreateService;

    public ProductCreateController(final ProductCreateService productCreateService) {
        this.productCreateService = productCreateService;
    }

    @PostMapping("/products")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid final ProductRequest productRequest) {
        final Product product = productCreateService.create(productRequest.getName(), productRequest.getImage(),
                productRequest.getPrice());
        final ProductResponse productResponse = ProductResponse.from(product);

        return ResponseEntity.created(URI.create("/products/" + product.getId()))
                .body(productResponse);
    }
}
