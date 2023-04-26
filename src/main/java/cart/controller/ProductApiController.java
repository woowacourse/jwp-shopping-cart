package cart.controller;

import cart.dto.request.ProductRequestDto;
import cart.service.ProductService;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/products")
public class ProductApiController {

    private final ProductService productService;

    public ProductApiController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ProductRequestDto productRequestDto) {
        final Long registeredProductId = productService.register(productRequestDto);
        return ResponseEntity.created(URI.create("/products/" + registeredProductId)).build();
    }
}
