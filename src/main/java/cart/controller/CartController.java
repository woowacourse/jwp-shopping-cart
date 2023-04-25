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

    @PostMapping("/admin/product")
    public ResponseEntity<String> registerProduct(@RequestBody ProductRequest productRequest) {
        adminService.registerProduct(productRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/admin/product/{id}")
    public ResponseEntity<String> updateProduct(@RequestBody ProductRequest productRequest, @PathVariable int id) {
//        System.out.println("id = " + id);
//        System.out.println("productRequest = " + productRequest);
        adminService.updateProduct(productRequest, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/admin/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        adminService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}