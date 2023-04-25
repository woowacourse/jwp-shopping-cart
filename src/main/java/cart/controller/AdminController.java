package cart.controller;

import cart.controller.dto.NewProductDto;
import cart.controller.dto.ProductDto;
import cart.service.CartService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final CartService cartService;

    @Autowired
    public AdminController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public String readProducts(final Model model) {
        final List<ProductDto> productDtos = cartService.findAll();
        model.addAttribute("products", productDtos);
        return "admin";
    }

    @PostMapping("/product")
    public ResponseEntity<Void> createProduct(@RequestBody @Valid NewProductDto newProductDto) {
        cartService.insert(newProductDto);
        return ResponseEntity.ok().build();
    }
}
