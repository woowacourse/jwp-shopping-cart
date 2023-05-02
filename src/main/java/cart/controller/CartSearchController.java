package cart.controller;

import java.util.List;

import cart.dto.ProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartSearchController {

    @GetMapping("/carts")
    public ResponseEntity<List<ProductResponse>> getCarts() {
        final List<ProductResponse> productResponses = List.of(
                new ProductResponse(1L, "odo", "url", 1),
                new ProductResponse(2L, "nunu", "url", 2)
        );
        return ResponseEntity.ok(productResponses);
    }
}
