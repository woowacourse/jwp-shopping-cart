package cart.controller;

import cart.domain.product.service.ProductService;
import cart.dto.ProductCreateRequest;
import cart.dto.ProductResponse;
import cart.dto.ProductUpdateRequest;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;

    public AdminController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public String home(Model model) {
        final List<ProductResponse> products = productService.findAll();
        model.addAttribute("products", products);
        return "admin";
    }

    @PostMapping("/products")
    public ResponseEntity<Void> add(@RequestBody final ProductCreateRequest productCreateRequest) {
        productService.create(productCreateRequest);
        return ResponseEntity.created(URI.create("/admin")).build();
    }

    @PatchMapping("/products/{id}")
    public ResponseEntity<Void> update(@PathVariable final Long id,
        @RequestBody final ProductUpdateRequest productUpdateRequest) {
        productUpdateRequest.setId(id);
        productService.update(productUpdateRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
