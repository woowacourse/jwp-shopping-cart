package cart.controller;

import java.net.URI;

import cart.dto.ProductRequest;
import cart.dto.ProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductCreateController {

    @PostMapping("/products")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody final ProductRequest productRequest) {
        final ProductResponse productResponse = new ProductResponse(1L, productRequest.getName(), productRequest.getImage(), productRequest.getPrice());

        return ResponseEntity.created(URI.create("/products/1")).body(productResponse);
    }
}
