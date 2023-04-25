package cart.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import cart.entity.Product;

@Controller
public class JwpCartController {

    @GetMapping("/")
    public String getProductList(Model model) {
        List<Product> products = new ArrayList<>();
        Product product = new Product(1L, "chicken", new byte[]{}, 100);
        Product product2 = new Product(2L, "pork", new byte[]{}, 200);

        products.add(product);
        products.add(product2);

        model.addAttribute("products", products);

        return "index";
    }

}
