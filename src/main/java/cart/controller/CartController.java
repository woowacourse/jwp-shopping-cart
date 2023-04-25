package cart.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cart.dto.ProductRequest;
import cart.dto.ProductResponse;
import cart.service.CartService;

@Controller
public class CartController {

    private final CartService cartService;

    public CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/product")
    @ResponseBody
    public void createProduct(@ModelAttribute final ProductRequest productRequest) {
        cartService.create(productRequest);
    }

    @GetMapping("/products")
    public String getProductList(final Model model) {
        List<ProductResponse> products = cartService.read();
        model.addAttribute("products", products);
        return "index";
    }

    @PutMapping("/product")
    public void updateProduct(@ModelAttribute final ProductRequest productRequest) {
        cartService.update(productRequest);
    }

    @DeleteMapping("/product")
    public void deleteProduct(@ModelAttribute final ProductRequest productRequest) {
        cartService.delete(productRequest);
    }
}
