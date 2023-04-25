package cart.controller;

import cart.controller.dto.ProductDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {

    @GetMapping("/")
    public String index(Model model) {
        ProductDto productDto = new ProductDto(
                1,
                "치킨",
                "https://grepp-programmers.s3.ap-northeast-2.amazonaws.com/files/production/d440b8f4-91c3-4272-8a81-876e9aaffb9c/RisingStarGraphBox.jpg",
                10000
        );
        model.addAttribute("products", productDto);
        return "index";
    }
}
