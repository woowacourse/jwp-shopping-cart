package cart.controller;

import cart.dto.ProductCreateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody ProductCreateRequest request) {
        System.out.println(request.getName());
        System.out.println(request.getPrice());
        System.out.println(request.getImageUrl());
        return ResponseEntity.ok("hello");
    }
}
