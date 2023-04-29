package cart.controller;

import cart.controller.dto.ProductDto;
import cart.domain.ProductCategory;
import cart.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;

    public AdminController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String getProducts(final Model model) {
        final List<ProductDto> products = productService.getProducts();
        model.addAttribute("products", products);
        return "admin";
    }

    @PostMapping
    public ResponseEntity<Void> addProduct(@RequestBody @Valid final ProductDto productDto) {
        final long productId = productService.save(productDto);
        return ResponseEntity.created(URI.create("/" + productId)).build();
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Void> updateProduct(
            @PathVariable final Long productId,
            @RequestBody @Valid final ProductDto productDto) {
        productService.update(productId, productDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable final Long productId) {
        productService.delete(productId);
        return ResponseEntity.noContent().build();
    }

    @ModelAttribute("categories")
    public List<ProductCategory> productCategories() {
        return List.of(ProductCategory.values());
    }
}
