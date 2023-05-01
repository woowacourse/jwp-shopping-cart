package cart.controller;

import cart.dto.ProductRequest;
import cart.dto.ProductResponse;
import cart.service.ProductService;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminRestController {

    private final ProductService productService;

    public AdminRestController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    public ResponseEntity<Void> addProduct(@Valid @RequestBody final ProductRequest productRequest) {
        Long id = productService.saveProduct(productRequest);
        return ResponseEntity.created(URI.create("products/" + id)).build();
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponse>> getAllProduct() {
        List<ProductResponse> products = productService.findAll();
        return ResponseEntity.ok(products);
    }

    @PatchMapping("/products/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable final Long id, @Valid @RequestBody ProductRequest productRequest) {
        productService.updateProduct(id, productRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Long> deleteProduct(@PathVariable final Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(id);
    }
}
