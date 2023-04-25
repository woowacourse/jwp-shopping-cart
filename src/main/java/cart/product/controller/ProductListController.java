package cart.product.controller;

import cart.product.dto.ProductDto;
import cart.product.service.ProductListService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductListController {
    
    private final ProductListService productListService;
    
    public ProductListController(final ProductListService productListService) {
        this.productListService = productListService;
    }
    
    @GetMapping("/")
    public String renderProductListPage(final Model model) {
        final List<ProductDto> productDtos = this.productListService.display();
        model.addAttribute("products", productDtos);
        return "index";
    }
}
