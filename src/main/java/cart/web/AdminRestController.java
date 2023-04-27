package cart.web;

import cart.domain.product.AdminService;
import cart.domain.product.dto.ProductCreationDto;
import cart.domain.product.dto.ProductModificationDto;
import cart.web.dto.request.ProductCreationRequest;
import cart.web.dto.request.ProductModificationRequest;
import cart.web.dto.response.ProductCreationResponse;
import cart.web.dto.response.ProductDeleteResponse;
import cart.web.dto.response.ProductModificationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/admin")
@RestController
public class AdminRestController {
    private final AdminService adminService;

    public AdminRestController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    public ResponseEntity<ProductCreationResponse> createProduct(@RequestBody ProductCreationRequest request) {
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
                .body(productCreationResponse);
    }

    @DeleteMapping("/{deleteId}")
    public ResponseEntity<ProductDeleteResponse> deleteProduct(@PathVariable Long deleteId) {
        adminService.delete(deleteId);

        return ResponseEntity
                .ok(new ProductDeleteResponse(deleteId));
    }

    @PutMapping
    public ResponseEntity<ProductModificationResponse> updateProduct(@RequestBody ProductModificationRequest request) {
        ProductModificationDto productModificationDto = new ProductModificationDto(
                request.getId(),
                request.getName(),
                request.getPrice(),
                request.getCategory(),
                request.getImageUrl()
        );

        adminService.update(productModificationDto);
        
        return ResponseEntity
                .ok(new ProductModificationResponse(request));
    }
}
