package cart.controller;

import cart.controller.dto.request.ProductCreationRequest;
import cart.controller.dto.request.ProductUpdateRequest;
import cart.service.product.ProductCreateService;
import cart.service.product.ProductDeleteService;
import cart.service.product.ProductUpdateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductCreateService productCreateService;
    private final ProductUpdateService productUpdateService;
    private final ProductDeleteService productDeleteService;

    public ProductController(final ProductCreateService productCreateService,
                             final ProductUpdateService productUpdateService,
                             final ProductDeleteService productDeleteService
    ) {
        this.productCreateService = productCreateService;
        this.productUpdateService = productUpdateService;
        this.productDeleteService = productDeleteService;
    }

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody @Valid final ProductCreationRequest request) {
        productCreateService.save(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<String> updateProduct(@RequestBody @Valid final ProductUpdateRequest request) {
        productUpdateService.update(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") @NotNull(message = "아이디가 비어있습니다.") Long id) {
        productDeleteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
