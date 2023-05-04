package cart.web.controller.admin;

import cart.domain.admin.AdminService;
import cart.domain.product.dto.ProductCreationDto;
import cart.domain.product.dto.ProductModificationDto;
import cart.exception.GlobalException;
import cart.web.controller.admin.dto.request.ProductModificationRequest;
import cart.web.controller.admin.dto.response.ProductCreationResponse;
import cart.web.controller.admin.dto.response.ProductModificationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequestMapping("/admin")
@RestController
public class AdminRestController {
    private final AdminService adminService;

    public AdminRestController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/products")
    public ResponseEntity<ProductCreationResponse> createProduct(@RequestBody ProductModificationRequest request) {
        ProductCreationDto productCreationDto = new ProductCreationDto(
                request.getName(),
                request.getPrice(),
                request.getCategory(),
                request.getImageUrl()
        );

        Long savedProductId = adminService.save(productCreationDto);

        ProductCreationResponse productCreationResponse =
                new ProductCreationResponse(savedProductId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/products/" + savedProductId))
                .body(productCreationResponse);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        adminService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/products/{id}")
    public ResponseEntity<ProductModificationResponse> updateProduct(@PathVariable Long id,
                                                                     @RequestBody ProductModificationRequest request) {
        ProductModificationDto productModificationDto = new ProductModificationDto(
                id,
                request.getName(),
                request.getPrice(),
                request.getCategory(),
                request.getImageUrl()
        );

        int countOfUpdate = adminService.update(productModificationDto);

        if (countOfUpdate == 0) {
            throw new GlobalException("존재하지 않는 리소스 입니다.");
        }

        return ResponseEntity
                .ok(new ProductModificationResponse(id, request));
    }
}
