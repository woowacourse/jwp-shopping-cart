package cart.controller;

import cart.dto.ProductResponse;
import cart.service.ProductQueryService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductSearchController {

    private final ProductQueryService ProductQueryService;

    public ProductSearchController(final ProductQueryService ProductQueryService) {
        this.ProductQueryService = ProductQueryService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        final List<ProductResponse> productResponses = ProductQueryService.find()
                .stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productResponses);
    }
}
