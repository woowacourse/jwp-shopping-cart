package cart.web;

import cart.domain.product.AdminService;
import cart.domain.product.dto.ProductCreationDto;
import cart.web.dto.ProductCreateRequest;
import cart.web.dto.ProductCreateResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/admin")
@Controller
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public String loadAdminPage() {
        return "admin";
    }

    @ResponseBody
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
}
