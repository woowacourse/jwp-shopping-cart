package cart.controller;

import java.util.List;

import cart.dto.ProductResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TemplateController {

    @GetMapping("/")
    public String getIndexPage(final Model model) {
        model.addAttribute("products", List.of(
                new ProductResponse(1, "누누", "naver.com", 1),
                new ProductResponse(2, "오도", "naver.com", 1)
        ));
        return "index";
    }
}
