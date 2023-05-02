package cart.controller.view;

import cart.dto.response.ProductResponse;
import cart.service.ProductsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductsService productsService;

    public AdminController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping
    public String admin(Model model) {
        List<ProductResponse> productsResponse = productsService.readAll();
        model.addAttribute("products", productsResponse);
        return "admin";
    }
}
