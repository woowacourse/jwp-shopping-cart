package cart.controller.view;

import cart.dto.response.ProductResponse;
import cart.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;

    public AdminController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String getAdminView(Model model) {
        List<ProductResponse> productResponses = productService.getAllProducts();
        model.addAttribute("products", productResponses);
        return "admin";
    }
}
