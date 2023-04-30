package cart.controller.admin;

import cart.dto.ProductModifyRequest;
import cart.service.ProductService;
import cart.dto.ProductRegisterRequest;
import cart.dto.ProductResponse;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String showAllProducts(Model model) {
        List<ProductResponse> allProducts = productService.findAllProducts();
        model.addAttribute("products", allProducts);
        return "admin";
    }

    @PostMapping("/product")
    public ResponseEntity<Void> registerProduct(@RequestBody @Valid ProductRegisterRequest productRegisterRequest) {
        long savedId = productService.save(productRegisterRequest);
        return ResponseEntity.created(URI.create("/admin/product/" + savedId)).build();
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<Void> modifyProduct(@RequestBody @Valid ProductModifyRequest productModifyRequest,
                                              @PathVariable long id) {
        productService.modifyById(productModifyRequest, id);
        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Void> removeProduct(@PathVariable long id) {
        productService.removeById(id);
        return ResponseEntity.noContent().build();
    }
}
