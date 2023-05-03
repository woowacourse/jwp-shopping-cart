package cart.controller.view;

import cart.dto.response.ProductResponse;
import cart.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProductListController {

    private final ProductService productService;

    public ProductListController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String getProductListView(Model model) {
        List<ProductResponse> productResponses = productService.getAllProducts();
        model.addAttribute("products", productResponses);
        return "index";
    }
}
