package cart.presentation;

import cart.application.ProductCRUDApplication;
import cart.business.ProductCRUDService;
import cart.presentation.dto.ProductDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TemplateController {

    private final ProductCRUDApplication productCRUDApplication;

    public TemplateController(ProductCRUDApplication productCRUDApplication) {
        this.productCRUDApplication = productCRUDApplication;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<ProductDto> products = productCRUDApplication.readAll();
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        List<ProductDto> products = productCRUDApplication.readAll();
        model.addAttribute("products", products);
        return "admin";
    }
}
