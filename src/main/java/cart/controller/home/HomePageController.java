package cart.controller.home;

import cart.dto.ProductDto;
import cart.service.ProductManagementService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomePageController {

    private final ProductManagementService productManagementService;

    public HomePageController(final ProductManagementService productManagementService) {
        this.productManagementService = productManagementService;
    }

    @GetMapping("/")
    public String readHomePage(final Model model) {
        final List<ProductDto> products = productManagementService.findAllProduct();
        model.addAttribute("products", products);
        return "index";
    }
}
