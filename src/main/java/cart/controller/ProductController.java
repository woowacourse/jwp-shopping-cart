package cart.controller;

import cart.domain.Product;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProductController {
    @RequestMapping("/")
    public ModelAndView productList() {
        ModelAndView mav = new ModelAndView("/index");

        Product chicken = new Product(1L,
                "치킨",
                "https://img.freepik.com/free-photo/crispy-fried-chicken-on-a-plate-with-salad-and-carrot_1150-20212.jpg",
                10000);
        Product tteokbokki = new Product(2L,
                "떡볶이",
                "https://img.freepik.com/free-photo/crispy-fried-chicken-on-a-plate-with-salad-and-carrot_1150-20212.jpg",
                20000);

        List<Product> products = List.of(chicken, tteokbokki);

        mav.addObject("products", products);

        return mav;
    }
}
