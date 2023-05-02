package cart.controller;

import cart.service.ProductService;
import cart.service.UserService;
import cart.service.dto.ProductDto;
import cart.service.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ViewController {

    private final ProductService productService;
    private final UserService userService;

    public ViewController(final ProductService productService, final UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping(path = "/")
    public String main(final Model model) {
        final List<ProductDto> products = productService.findAll();
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping(path = "/admin")
    public String admin(Model model) {
        final List<ProductDto> products = productService.findAll();
        model.addAttribute("products", products);
        return "admin";
    }

    @GetMapping(path = "/settings")
    public String settings(Model model) {
        final List<UserDto> users = userService.findAll();
        model.addAttribute("members", users);
        return "settings";
    }
}
