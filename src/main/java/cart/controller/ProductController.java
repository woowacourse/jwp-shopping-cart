package cart.controller;

import cart.dto.ProductCreationRequest;
import cart.dto.ProductDto;
import cart.dto.ProductModificationRequest;
import cart.dto.ProductResponse;
import cart.mapper.ProductDtoMapper;
import cart.mapper.ProductResponseMapper;
import cart.service.ProductManagementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/products")
@RestController
public class ProductController {

    private final ProductManagementService managementService;

    public ProductController(final ProductManagementService managementService) {
        this.managementService = managementService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts() {
        final List<ProductDto> products = managementService.findAll();
        return ResponseEntity.ok(ProductResponseMapper.from(products));
    }

    @PostMapping
    public ResponseEntity<Void> postProducts(@Valid @RequestBody ProductCreationRequest request) {
        managementService.save(ProductDtoMapper.from(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Void> putProducts(@PathVariable Long productId,
                                            @Valid @RequestBody ProductModificationRequest request) {
        managementService.updateById(productId, ProductDtoMapper.from(request));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProducts(@PathVariable Long productId) {
        managementService.deleteById(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
