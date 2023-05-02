package cart.controller;

import cart.controller.dto.request.product.ProductInsertRequest;
import cart.controller.dto.request.product.ProductUpdateRequest;
import cart.service.ProductService;
import java.net.URI;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/products")
@RestController
public class ProductApiController {

    private final ProductService productService;

    public ProductApiController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Integer> create(@Valid @RequestBody final ProductInsertRequest productInsertRequest) {
        Integer savedId = productService.insert(productInsertRequest);
        return ResponseEntity.created(URI.create("/products/" + savedId)).body(savedId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable final int id,
                                       @Valid @RequestBody final ProductUpdateRequest productUpdateRequest) {
        productService.update(id, productUpdateRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final int id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
