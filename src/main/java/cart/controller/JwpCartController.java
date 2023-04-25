package cart.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import cart.dto.ProductResponseDto;
import cart.service.JwpCartService;

@Controller
public class JwpCartController {

    private final JwpCartService jwpCartService;

    public JwpCartController(JwpCartService jwpCartService) {
        this.jwpCartService = jwpCartService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<ProductResponseDto> all = jwpCartService.findAll();
        model.addAttribute("products", all);
        return "index";
    }
}
