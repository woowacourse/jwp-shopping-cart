package cart.web;

import cart.domain.product.AdminService;
import cart.domain.product.dto.ProductCreationDto;
import cart.web.dto.ProductCreateRequest;
import cart.web.dto.ProductCreateResponse;
import cart.web.dto.ProductDeleteResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseEntity<ProductCreateResponse> createProduct(@RequestBody ProductCreateRequest request) {
        ProductCreationDto productCreationDto = new ProductCreationDto(
                request.getName(),
                request.getPrice(),
                request.getCategory(),
                request.getImageUrl()
        );

        Long savedProductId = adminService.save(productCreationDto);

        ProductCreateResponse productCreateResponse =
                new ProductCreateResponse(savedProductId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productCreateResponse);
    }

    @DeleteMapping("/{deleteId}")
    public ResponseEntity<ProductDeleteResponse> deleteProduct(@PathVariable Long deleteId) {
        adminService.delete(deleteId);

        return ResponseEntity
                .ok(new ProductDeleteResponse(deleteId));
    }
}
