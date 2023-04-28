package cart.controller;

import java.util.List;
import java.util.stream.Collectors;

import cart.dto.ProductResponse;
import cart.service.ProductSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductSearchController {

    private final ProductSearchService productSearchService;

    public ProductSearchController(final ProductSearchService productSearchService) {
        this.productSearchService = productSearchService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        final List<ProductResponse> productResponses = productSearchService.findAll()
                .stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productResponses);
    }
}
