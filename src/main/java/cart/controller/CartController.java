package cart.controller;

import cart.dto.ProductRequest;
import cart.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CartController {

    private final AdminService adminService;

    public CartController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/admin/product")
    public ResponseEntity<String> addProduct(@RequestBody ProductRequest productRequest) {
        adminService.addProduct(productRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/admin/product/{id}")
    public ResponseEntity<String> updateProduct(@RequestBody ProductRequest productRequest, @PathVariable int id) {
        adminService.updateProduct(productRequest, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/admin/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        adminService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}