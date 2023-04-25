package cart.controller;

import java.util.List;

import cart.dto.ProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
}
