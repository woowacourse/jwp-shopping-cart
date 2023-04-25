package cart.controller;

import cart.dto.ProductRequestDto;
import cart.dto.ProductResponseDto;
import cart.service.ProductService;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private ProductService productService;

    public AdminController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public String admin(Model model) {
        List<ProductResponseDto> products = productService.findAll();
        model.addAttribute("products", products);
        return "admin";
    }

    @PostMapping("/products")
    public ResponseEntity<Void> addProduct(@RequestBody final ProductRequestDto productRequestDto) {
        Long id = productService.saveProduct(productRequestDto);
        return ResponseEntity.created(URI.create("admin/products/"+id)).build();
    }

    @PatchMapping("products/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable final Long id, @RequestBody ProductRequestDto productRequestDto) {
        productService.updateProduct(id, productRequestDto);
        return ResponseEntity.noContent().build();
    }
}
