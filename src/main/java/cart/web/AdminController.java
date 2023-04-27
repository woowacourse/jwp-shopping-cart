package cart.web;

import cart.domain.product.AdminService;
import cart.domain.product.CartService;
import cart.domain.product.dto.ProductCreationDto;
import cart.domain.product.dto.ProductDto;
import cart.web.dto.ProductCreateRequest;
import cart.web.dto.ProductCreateResponse;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/admin")
@Controller
public class AdminController {
    private final AdminService adminService;
    private final CartService cartService;

    public AdminController(AdminService adminService, CartService cartService) {
        this.adminService = adminService;
        this.cartService = cartService;
    }

    @GetMapping
    public String loadAdminPage(Model model) {
        List<ProductDto> allProducts = cartService.getAllProducts();

        model.addAttribute("products", allProducts);

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
