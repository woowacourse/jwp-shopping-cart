package cart.controller;

import java.net.URI;
import java.util.List;

import cart.dto.ProductRequest;
import cart.dto.ProductResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        final List<ProductResponse> productResponses = List.of(
                new ProductResponse(1, "누누", "naver.com", 1),
                new ProductResponse(2, "오도", "naver.com", 1)
        );
        return ResponseEntity.ok(productResponses);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable final long id, @RequestBody final ProductRequest productRequest) {
        final ProductResponse productResponse = new ProductResponse(id, productRequest.getName(), productRequest.getImage(), productRequest.getPrice());

        return ResponseEntity.ok(productResponse);
    }

    @PostMapping("/products")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody final ProductRequest productRequest) {
        final ProductResponse productResponse = new ProductResponse(1L, productRequest.getName(), productRequest.getImage(), productRequest.getPrice());

        return ResponseEntity.created(URI.create("/products/1")).body(productResponse);
    }

    @DeleteMapping("/products/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable final long id) {
    }
}
