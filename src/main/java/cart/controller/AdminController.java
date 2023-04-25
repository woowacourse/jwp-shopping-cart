package cart.controller;

import cart.controller.dto.ProductDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    public String findProduct(Model model) {
        List<ProductDto> productDtos = List.of(
                new ProductDto(
                        1,
                        "치킨",
                        "https://grepp-programmers.s3.ap-northeast-2.amazonaws.com/files/production/d440b8f4-91c3-4272-8a81-876e9aaffb9c/RisingStarGraphBox.jpg",
                        10000
                ),
                new ProductDto(
                        2,
                        "별모양핫도그",
                        "https://grepp-programmers.s3.ap-northeast-2.amazonaws.com/files/production/d440b8f4-91c3-4272-8a81-876e9aaffb9c/RisingStarGraphBox.jpg",
                        53000
                )
        );

        model.addAttribute("products", productDtos);
        return "admin";
    }
}
