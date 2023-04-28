package cart.controller;

import cart.dto.ProductRequestDto;
import cart.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/products")
    public ResponseEntity<String> addProduct(@Valid @RequestBody ProductRequestDto productRequestDto) {
        adminService.addProduct(productRequestDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<String> updateProduct(@Valid @RequestBody ProductRequestDto productRequestDto, @PathVariable int id) {
        adminService.updateProduct(productRequestDto, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        adminService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
