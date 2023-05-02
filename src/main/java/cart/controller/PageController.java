package cart.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import cart.dto.ProductDto;
import cart.service.AdminService;

@Controller
public class PageController {

    private final AdminService adminService;

    public PageController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/")
    String allProducts(Model model) {
        List<ProductDto> productEntities = adminService.selectAllProducts();
        model.addAttribute("products", productEntities);
        return "index";
    }

    @GetMapping("/admin")
    String adminAllProducts(Model model) {
        List<ProductDto> productEntities = adminService.selectAllProducts();
        model.addAttribute("products", productEntities);
        return "admin";
    }
}
