package cart.controller;

import cart.service.CartService;
import cart.service.dto.ProductRequest;
import cart.service.dto.ProductResponse;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/admin")
    public String showAllProducts(Model model) {
        List<ProductResponse> allProducts = cartService.findAllProducts();
        model.addAttribute("products", allProducts);
        return "admin";
    }

    @PostMapping("/admin/product")
    @ResponseBody
    public ResponseEntity<Void> registerProduct(@RequestBody @Valid ProductRequest productRequest) {
        long savedId = cartService.save(productRequest);
        return ResponseEntity.created(URI.create("/admin/product/" + savedId)).build();
    }

    @PutMapping("/admin/product/{id}")
    @ResponseBody
    public void modifyProduct(@RequestBody @Valid ProductRequest productRequest, @PathVariable long id) {
        cartService.modify(productRequest, id);
    }
}
