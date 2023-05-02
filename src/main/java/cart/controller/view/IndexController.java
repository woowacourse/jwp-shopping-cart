package cart.controller.view;

import cart.dto.response.ProductResponse;
import cart.service.ProductsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {

    private final ProductsService productsService;

    public IndexController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index(Model model) {
        List<ProductResponse> productsResponse = productsService.readAll();
        model.addAttribute("products", productsResponse);
        return "index";
    }
}
