package cart.controller;

import cart.dto.ProductDto;
import cart.dto.request.ProductAddRequest;
import cart.dto.request.ProductUpdateRequest;
import cart.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductApiController {

    private final ProductService productService;

    @Autowired
    public ProductApiController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Void> add(@RequestBody @Valid ProductAddRequest productAddRequest) {
        final long id = productService.register(productAddRequest);
        return ResponseEntity.created(URI.create("/products" + id))
                             .build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody @Valid ProductUpdateRequest productUpdateRequest) {
        final ProductDto productDto = productService.update(productUpdateRequest);
        return ResponseEntity.created(URI.create("/products" + productDto.getId()))
                             .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("id={}", id);
        productService.delete(id);
        return ResponseEntity.noContent()
                             .build();
    }
}
