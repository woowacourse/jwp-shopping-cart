package cart.controller.view;

import cart.controller.dto.response.ProductResponse;
import cart.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {

    private final ProductService productService;

    public IndexController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String index(final Model model) {
        List<ProductResponse> responses = productService.findAll();
        model.addAttribute("products", responses);
        return "index";
    }

}
