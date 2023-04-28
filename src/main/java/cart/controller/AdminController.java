package cart.controller;

import cart.controller.dto.ProductDto;
import cart.persistence.entity.ProductCategory;
import cart.service.ShoppingService;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ShoppingService shoppingService;

    public AdminController(final ShoppingService shoppingService) {
        this.shoppingService = shoppingService;
    }

    @GetMapping
    public String getProducts(final Model model) {
        final List<ProductDto> products = shoppingService.getProducts();
        model.addAttribute("products", products);
        return "admin";
    }

    @PostMapping
    public ResponseEntity<Void> addProduct(@RequestBody @Valid final ProductDto productDto) {
        final Long productId = shoppingService.save(productDto);
        return ResponseEntity.created(URI.create("/admin/" + productId)).build();
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Void> updateProduct(
            @PathVariable final Long productId,
            @RequestBody @Valid final ProductDto productDto) {
        shoppingService.update(productId, productDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> deleteProduct(@PathVariable final Long productId) {
        shoppingService.delete(productId);
        return ResponseEntity.noContent().build();
    }

    @ModelAttribute("categorys")
    public List<ProductCategory> productCategories() {
        return List.of(ProductCategory.values());
    }
}
