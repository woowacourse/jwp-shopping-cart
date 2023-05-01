package cart.controller;

import cart.dto.ResponseProductDto;
import cart.dto.ResponseUserDto;
import cart.service.ProductService;
import cart.service.UserService;
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

    @GetMapping("/")
    public String index(final Model model) {
        final List<ResponseProductDto> responseProductDtos = productService.findAll();
        model.addAttribute("products", responseProductDtos);
        return "index";
    }

    @GetMapping("/admin")
    public String admin(final Model model) {
        final List<ResponseProductDto> responseProductDtos = productService.findAll();
        model.addAttribute("products", responseProductDtos);
        return "admin";
    }

    @GetMapping("/settings")
    public String settings(final Model model) {
        final List<ResponseUserDto> responseUserDtos = userService.findAll();
        model.addAttribute("members", responseUserDtos);
        return "settings";
    }
}
