package cart.web.admin.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import cart.domain.admin.AdminService;
import cart.web.admin.dto.ProductResponse;

@Controller
public class AdminViewController {

    private final AdminService adminService;

    public AdminViewController(final AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/admin")
    public String getAllProducts(final Model model) {
        final List<ProductResponse> responses = adminService.findAll().stream()
            .map(ProductResponse::from)
            .collect(Collectors.toList());
        model.addAttribute("products", responses);
        return "admin";
    }
}
