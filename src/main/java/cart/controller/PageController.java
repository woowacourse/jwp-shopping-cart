package cart.controller;

import cart.entity.ProductEntity;
import cart.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PageController {

    private final AdminService adminService;

    public PageController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/")
    String allProducts(Model model) {
        return userAllProducts(model);
    }

    @GetMapping("/products")
    String userAllProducts(Model model) {
        List<ProductEntity> productEntities = adminService.selectAllProducts();
        model.addAttribute("products", productEntities);
        return "index";
    }

    @GetMapping("/admin")
    String adminAllProducts(Model model) {
        List<ProductEntity> productEntities = adminService.selectAllProducts();
        model.addAttribute("products", productEntities);
        return "admin";
    }
}
