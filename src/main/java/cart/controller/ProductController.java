package cart.controller;

import cart.controller.dto.ProductSaveRequest;
import cart.controller.dto.ProductUpdateRequest;
import cart.service.ProductService;
import cart.service.dto.ProductSaveDto;
import cart.service.dto.ProductUpdateDto;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Void> saveProduct(@Valid @RequestBody final ProductSaveRequest productSaveRequest) {
        this.productService.save(ProductSaveDto.from(productSaveRequest));
        return ResponseEntity.ok().build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(
            @Valid @RequestBody final ProductUpdateRequest productUpdateRequest,
            @NotNull @PathVariable Long id
    ) {
        this.productService.update(ProductUpdateDto.createWithIdAndRequest(id, productUpdateRequest));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@NotNull @PathVariable Long id) {
        this.productService.deleteById(id);
        return ResponseEntity.noContent().build();

    }
}
