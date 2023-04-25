package cart.controller;

import cart.domain.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {

    @GetMapping("/")
    public String productList(Model model) {
        List<Product> products = new ArrayList<>();
        Product product = new Product(1L, "name", "https://i.namu.wiki/i/wUwZSCH7x8awZvC7yyMqzsbk21IhsN8u9zKc-SKRfD9XvpjXibqJ9ChjcQC-QRB7H6SobmbqSXkF7-1b523G1-Mrz-eP3IO3mWA8wUrbiSIQINQSFHwESDGg2YZ3upY9e-zb19_2t6Y4Vnd7SZVpDQ.webp", 1000);

        products.add(product);
        model.addAttribute("products", products);
        return "index";
    }
}
