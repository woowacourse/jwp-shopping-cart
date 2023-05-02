package cart.controller;


import cart.entity.User;
import cart.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {
    private final ProductService productService;

    public CartController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String showProductsList(Model model) {
        model.addAttribute("products", productService.findProducts());
        return "index";
    }

    @GetMapping("/admin")
    public String showAdmin(Model model) {
        model.addAttribute("products", productService.findProducts());
        return "admin";
    }

    @GetMapping("/settings")
    public String showUsers(Model model) {
        model.addAttribute("members", new User("jaehyun@naver.com", "비밀번호입니당"));
        return "settings";
    }
}
