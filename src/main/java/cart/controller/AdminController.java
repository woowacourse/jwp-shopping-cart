package cart.controller;

import cart.dto.request.ProductRequest;
import cart.dto.response.ProductResponse;
import cart.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;

    public AdminController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody @Valid ProductRequest productRequest) {
        Long savedId = productService.create(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(URI.create("/admin"))
                .build();
    }

    @GetMapping
    public String admin(Model model) {
        List<ProductResponse> products = productService.findAll();
        model.addAttribute("products", products);
        return "admin";
    }

    @PutMapping("/{productId}")
    public ResponseEntity<String> update(@PathVariable Long productId,
                                         @RequestBody @Valid ProductRequest productRequest) {
        productService.update(productId, productRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .location(URI.create("/admin"))
                .body("ok");
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> delete(@PathVariable Long productId) {
        productService.deleteById(productId);
        return ResponseEntity.status(HttpStatus.OK)
                .location(URI.create("/admin"))
                .body("ok");
    }
}
