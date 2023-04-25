package cart.controller;

import cart.domain.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model) {
        List<Product> products = new ArrayList<>();
        products.add(new Product("a", "url", 1));
        products.add(new Product("b", "url", 2));
        products.add(new Product("c", "url", 3));

        model.addAttribute("products", products);
        return "index";
    }

}
