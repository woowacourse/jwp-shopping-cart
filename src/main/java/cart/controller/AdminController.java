package cart.controller;

import cart.dto.ProductRequest;
import cart.dto.ProductResponse;
import cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final CartService cartService;

    public AdminController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/product")
    public ResponseEntity<Void> createProduct(@RequestBody @Valid final ProductRequest productRequest) {
        final long id = cartService.create(productRequest);
        return ResponseEntity.created(URI.create("/admin/product/" + id)).build();
    }

    @GetMapping
    public String getProductList(final Model model) {
        List<ProductResponse> products = cartService.read();
        model.addAttribute("products", products);
        return "admin";
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable Long id, @RequestBody final ProductRequest productRequest) {
        cartService.update(id, productRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        cartService.delete(id);
        return ResponseEntity.ok().build();
    }
}
