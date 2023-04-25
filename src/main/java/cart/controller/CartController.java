package cart.controller;

import cart.service.CartService;
import cart.service.dto.ProductRequest;
import cart.service.dto.ProductResponse;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

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
    @ResponseStatus(HttpStatus.CREATED)
    public void registerProduct(@RequestBody @Valid ProductRequest productRequest) {
        cartService.save(productRequest);
    }
}
