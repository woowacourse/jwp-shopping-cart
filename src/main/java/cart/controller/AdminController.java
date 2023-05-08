package cart.controller;

import cart.domain.product.dto.ProductCreateDto;
import cart.domain.product.dto.ProductUpdateDto;
import cart.domain.product.service.ProductService;
import cart.dto.ProductCreateRequest;
import cart.dto.ProductUpdateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final ProductService productService;

    public AdminController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    public ResponseEntity<Void> add(@RequestBody final ProductCreateRequest productCreateRequest) {
        final ProductCreateDto productCreateDto = ProductCreateDto.of(productCreateRequest);
        productService.create(productCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/products/{id}")
    public ResponseEntity<Void> update(@PathVariable final Long id,
        @RequestBody final ProductUpdateRequest productUpdateRequest) {
        final ProductUpdateDto productUpdateDto = ProductUpdateDto.of(id, productUpdateRequest);
        productService.update(productUpdateDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
