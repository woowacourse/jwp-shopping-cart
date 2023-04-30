package cart.controller;

import cart.dto.ProductResponse;
import cart.dto.ProductSaveRequest;
import cart.dto.ProductUpdateRequest;
import cart.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;

    public AdminController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @ModelAttribute("products")
    public List<ProductResponse> admin() {
        return productService.findAll();
    }

    @PostMapping("/product")
    public ResponseEntity<Void> saveProduct(@Valid @RequestBody final ProductSaveRequest productSaveRequest) {
        final Long id = productService.save(productSaveRequest);
        return ResponseEntity.created(URI.create("/admin/product/" + id)).build();
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<Void> updateProduct(
            @PathVariable final Long id,
            @Valid @RequestBody final ProductUpdateRequest productUpdateRequest) {
        productService.update(id, productUpdateRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable final Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
