package cart.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    public String loadHome(Model model) {

        List<ProductResponse> products = new ArrayList<>();

        ProductResponse product = new ProductResponse(3L, "onigiri", 3800,
            "https://ssl.pstatic.net/melona/libs/1442/1442607/3cbc04bb1da9138cb6e2_20230424140340572.jpg");
        products.add(product);

        model.addAttribute("products", products);
        return "admin";
    }

}
