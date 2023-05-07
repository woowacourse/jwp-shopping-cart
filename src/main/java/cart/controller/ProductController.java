package cart.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cart.dto.ProductRequest;
import cart.service.JwpCartService;

@RequestMapping("/products")
@RestController
public class ProductController {

    private final JwpCartService jwpCartService;

    public ProductController(JwpCartService jwpCartService) {
        this.jwpCartService = jwpCartService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addProduct(@RequestBody @Valid ProductRequest productRequest) {
        jwpCartService.addProduct(productRequest);
        return ResponseEntity.created(URI.create("/products")).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable Long id,
        @RequestBody @Valid ProductRequest productRequest) {
        jwpCartService.updateProductById(productRequest, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        jwpCartService.deleteProductById(id);
        return ResponseEntity.ok().build();
    }
}
