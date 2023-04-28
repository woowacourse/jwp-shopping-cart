package cart.presentation;

import cart.business.ReadProductService;
import cart.presentation.dto.ProductDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TemplateController {

    private final ReadProductService readProductService;

    public TemplateController(ReadProductService readProductService) {
        this.readProductService = readProductService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<ProductDto> products = readAllProducts();
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        List<ProductDto> products = readAllProducts();
        model.addAttribute("products", products);
        return "admin";
    }

    private List<ProductDto> readAllProducts() {
        return readProductService.perform().stream()
                .map(product -> new ProductDto(product.getId(),
                        product.getName(),
                        product.getUrl(),
                        product.getPrice()))
                .collect(Collectors.toList());
    }
}
