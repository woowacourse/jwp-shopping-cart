package cart.controller.view;

import cart.controller.rest.ProductsController;
import cart.dto.response.ProductResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProductListController {

    private final ProductsController productsController;

    public ProductListController(ProductsController productsController) {
        this.productsController = productsController;
    }

    @GetMapping
    public String getProductListView(Model model) {
        List<ProductResponse> productResponses = productsController.getProducts();
        model.addAttribute("products", productResponses);
        return "index";
    }
}
