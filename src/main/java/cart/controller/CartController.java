package cart.controller;

import cart.dto.CreateProductRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class CartController {

    @GetMapping("/products")
    public String products() {
        return "index";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @PostMapping("/admin/create")
    public ResponseEntity<String> create(@RequestBody CreateProductRequest createProductRequest) {
        return ResponseEntity.ok().build();
    }
}
