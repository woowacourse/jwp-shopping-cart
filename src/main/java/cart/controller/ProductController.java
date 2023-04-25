package cart.controller;

import cart.dto.ProductRequest;
import cart.dto.ProductResponse;
import cart.entity.Product;
import cart.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<ProductResponse> products = productService.findProducts().stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        List<ProductResponse> products = productService.findProducts().stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
        model.addAttribute("products", products);
        return "admin";
    }

    @PostMapping("/admin/products")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        Product product = new Product(productRequest.getName(), productRequest.getImgUrl(), productRequest.getPrice());
        Product createdProduct = productService.createProduct(product);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/")
                .build()
                .toUri();
        return ResponseEntity.created(location).body(createdProduct);
    }

    @PatchMapping("/admin/products/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable long id, @Valid @RequestBody ProductRequest productRequest) {
        Product product = new Product(id, productRequest.getName(), productRequest.getImgUrl(), productRequest.getPrice());
        productService.updateProduct(product);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/admin/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long id) {
        productService.deleteProductBy(id);
        return ResponseEntity.ok().build();
    }
}
