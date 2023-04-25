package cart.controller;

import cart.domain.Product;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {

    @GetMapping(path = "/")
    public String home(Model model) {
        model.addAttribute("products", List.of(
                new Product("치킨", "xxx", 10000),
                new Product("치킨", "xxx", 10000)
        ));

        return "index";
    }
}
