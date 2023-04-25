package cart.controller;

import cart.dto.ProductDto;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {

    @GetMapping("/")
    public String indexPage(Model model) {
        model.addAttribute("products", List.of(new ProductDto(1, "과자", "https://www.clickmall.kr/shopimages/clickmall/019007000099.jpg?1674614971", 1000)));
        return "index";
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("products", List.of(new ProductDto(1, "과자", "https://www.clickmall.kr/shopimages/clickmall/019007000099.jpg?1674614971", 1000)));
        return "admin";
    }
}
