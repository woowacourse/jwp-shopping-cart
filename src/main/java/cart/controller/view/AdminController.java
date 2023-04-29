package cart.controller.view;

import cart.controller.rest.ProductsController;
import cart.dto.response.ProductResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductsController productsController;

    public AdminController(ProductsController productsController) {
        this.productsController = productsController;
    }

    @GetMapping
    public String getAdminView(Model model) {
        List<ProductResponse> productResponses = productsController.getProducts();
        model.addAttribute("products", productResponses);
        return "admin";
    }
}
