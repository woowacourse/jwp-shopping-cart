package cart.controller;

import cart.dto.request.ProductRequest;
import cart.dto.response.ProductResponse;
import cart.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/admin/create")
    public String create(@RequestBody ProductRequest productRequest) {

        adminService.create(productRequest);
        return "admin";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        List<ProductResponse> products = adminService.findAll();
        model.addAttribute("products", products);
        return "admin";
    }


    @DeleteMapping("/admin/{productId}")
    public String delete(@PathVariable Long productId) {
        adminService.deleteById(productId);
        return "admin";
    }
}
