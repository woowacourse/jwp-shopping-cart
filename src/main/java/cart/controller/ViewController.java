package cart.controller;

import cart.dto.ProductResponse;
import cart.service.ProductFindService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ViewController {

    private final ProductFindService findService;

    public ViewController(final ProductFindService findService) {
        this.findService = findService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<ProductResponse> products = findService.findProducts().stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
        model.addAttribute("products", products);
        return "index.html";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        List<ProductResponse> products = findService.findProducts().stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
        model.addAttribute("products", products);
        return "admin.html";
    }
}
