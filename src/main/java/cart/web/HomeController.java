package cart.web;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import cart.domain.admin.AdminService;
import cart.web.admin.dto.ProductResponse;

@Controller
public class HomeController {

    private final AdminService adminService;

    public HomeController(final AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/")
    public String getAllProducts(final Model model) {
        final List<ProductResponse> response = adminService.findAll().stream()
            .map(ProductResponse::from)
            .collect(Collectors.toList());
        model.addAttribute("products", response);
        return "index";
    }
}
