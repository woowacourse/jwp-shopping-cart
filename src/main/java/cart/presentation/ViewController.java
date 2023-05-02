package cart.presentation;

import cart.business.ProductService;
import cart.presentation.dto.ProductResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ViewController {
    //field이름으로 판단해서 주입하기도 함
    //interface bean을 어떻게 주입하는가? 어떻게 알고 주입하는가?에 대한 내용임
    // 방법은 여러가지. 1. 우선순위주기 @Primary 2. 이름으로 주입, 3. @Qualifier

    private final ProductService readProductService;


    public ViewController(ProductService readProductService) {
        this.readProductService = readProductService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<ProductResponse> products = readProductService.read().stream()
                .map(product -> new ProductResponse(product.getId(),
                        product.getName(),
                        product.getUrl(),
                        product.getPrice()))
                .collect(Collectors.toList());
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        List<ProductResponse> products = readProductService.read().stream()
                .map(product -> new ProductResponse(product.getId(),
                        product.getName(),
                        product.getUrl(),
                        product.getPrice()))
                .collect(Collectors.toList());
        model.addAttribute("products", products);
        return "admin";
    }
}
