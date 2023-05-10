package cart.controller.admin;

import cart.dto.ProductDto;
import cart.service.ProductManagementService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminPageController {

    private final ProductManagementService productManagementService;

    public AdminPageController(final ProductManagementService productManagementService) {
        this.productManagementService = productManagementService;
    }

    @GetMapping("")
    public String readAdminPage(final Model model) {
        final List<ProductDto> products = productManagementService.findAllProduct();
        model.addAttribute("products", products);
        return "admin";
    }
}
