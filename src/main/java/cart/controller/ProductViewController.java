package cart.controller;

import cart.dto.application.ProductEntityDto;
import cart.service.product.ProductFindService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductViewController {

    private final ProductFindService productFindService;

    public ProductViewController(final ProductFindService productFindService) {
        this.productFindService = productFindService;
    }

    @GetMapping("/")
    public String indexPage(final Model model) {
        final List<ProductEntityDto> products = productFindService.findAll();

        model.addAttribute("products", products);

        return "index";
    }

    @GetMapping("/admin")
    public String adminPage(final Model model) {
        final List<ProductEntityDto> products = productFindService.findAll();

        model.addAttribute("products", products);

        return "admin";
    }
}
