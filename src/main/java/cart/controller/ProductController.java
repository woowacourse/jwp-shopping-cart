package cart.controller;

import cart.controller.dto.ProductCreationRequest;
import cart.controller.dto.ProductUpdateRequest;
import cart.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final AdminService adminService;

    public ProductController(final AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody @Valid final ProductCreationRequest request) {
        adminService.save(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<String> updateProduct(@RequestBody @Valid final ProductUpdateRequest request) {
        adminService.update(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") @NotNull(message = "아이디가 비어있습니다.") Long id) {
        adminService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
