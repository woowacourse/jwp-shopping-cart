package cart.presentation;

import cart.business.ReadProductService;
import cart.presentation.dto.ProductDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class WebController {

    private final ReadProductService readProductService;

    public WebController(ReadProductService readProductService) {
        this.readProductService = readProductService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<ProductDto> products = readProductService.perform().stream()
                .map(product -> new ProductDto(product.getId(),
                        product.getName(),
                        product.getUrl(),
                        product.getProductPrice()))
                .collect(Collectors.toList());
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        List<ProductDto> products = readProductService.perform().stream()
                .map(product -> new ProductDto(product.getId(),
                        product.getName(),
                        product.getUrl(),
                        product.getProductPrice()))
                .collect(Collectors.toList());

        products.add(new ProductDto(
                1,
                "123",
                "http://123",
                123
        ));
        model.addAttribute("products", products);
        return "admin";
    }
}
