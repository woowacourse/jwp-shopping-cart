package cart.controller;

import cart.dto.ProductCreationRequest;
import cart.dto.ProductDto;
import cart.dto.ProductModificationRequest;
import cart.mapper.ProductDtoMapper;
import cart.service.ProductManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/admin/products")
public class AdminProductController {

    private final ProductManagementService managementService;

    public AdminProductController(final ProductManagementService managementService) {
        this.managementService = managementService;
    }

    @PostMapping
    public ResponseEntity<Void> postProducts(@Valid @RequestBody ProductCreationRequest request) {
        final long id = managementService.save(ProductDtoMapper.from(request));
        return ResponseEntity.created(URI.create("/products/" + id)).build();
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Void> putProducts(@PathVariable Long productId,
                                            @Valid @RequestBody ProductModificationRequest request) {
        managementService.update(ProductDtoMapper.from(request));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProducts(@PathVariable Long productId) {
        managementService.delete(ProductDto.from(productId));
        return ResponseEntity.noContent().build();
    }
}
