package cart.controller;

import cart.domain.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class WebPageController {

    @GetMapping("/")
    public String renderStartPage(Model model) {
        List<Product> products = List.of(
                new Product("비버", "https://gmlwjd9405.github.io/images/network/rest.png", 10000L),
                new Product("땡칠", "https://gmlwjd9405.github.io/images/network/rest.png", 5000L)
        );
        model.addAttribute("products", products);
        return "index";
    }
}
