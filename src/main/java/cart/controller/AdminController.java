package cart.controller;

import cart.dto.ProductRequest;
import cart.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
    public String admin() {
        return "admin";
    }

}
