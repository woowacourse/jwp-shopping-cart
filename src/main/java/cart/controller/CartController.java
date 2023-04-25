package cart.controller;

import cart.dto.ProductRequest;
import cart.entity.ProductEntity;
import cart.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CartController {

    private final AdminService adminService;

    public CartController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/")
    String allProducts() {
        return "index";
    }

    @GetMapping("/products")
    String userAllProducts() {
        return "index";
    }

    @GetMapping("/admin")
    String adminAllProducts() {
        return "admin";
    }
}
