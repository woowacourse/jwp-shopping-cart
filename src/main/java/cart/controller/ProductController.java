package cart.controller;

import cart.dto.ProductRequestDto;
import cart.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/admin")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    public ResponseEntity<Void> addProduct(@RequestBody ProductRequestDto productRequestDto) {
        productService.add(productRequestDto);
        return ResponseEntity.created(URI.create("/admin/products")).build();
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Void> modifyProduct(@PathVariable int id, @RequestBody ProductRequestDto productRequestDto) {
        productService.modifyById(id, productRequestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> removeProduct(@PathVariable int id) {
        productService.removeById(id);
        return ResponseEntity.noContent().build();
    }

}
