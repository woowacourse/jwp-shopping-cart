package cart.web.controller;

import cart.domain.product.service.AdminService;
import cart.domain.product.service.dto.ProductCreationDto;
import cart.domain.product.service.dto.ProductModificationDto;
import cart.web.controller.dto.request.ProductCreationRequest;
import cart.web.controller.dto.request.ProductModificationRequest;
import cart.web.controller.dto.response.ProductCreationResponse;
import cart.web.controller.dto.response.ProductDeleteResponse;
import cart.web.controller.dto.response.ProductModificationResponse;
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
                .location(URI.create("/admin/" + savedProductId))
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
