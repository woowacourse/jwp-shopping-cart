package cart.controller;


import cart.service.CustomerService;
import cart.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    public final CustomerService customerService;
    private final ProductService productService;

    public ViewController(final ProductService productService, final CustomerService customerService) {
        this.productService = productService;
        this.customerService = customerService;
    }

    @GetMapping("/admin")
    public String adminPage(final Model model) {
        model.addAttribute("products", productService.findAll());
        return "admin";
    }

    @GetMapping
    public String rootPage(Model model) {
        model.addAttribute("products", productService.findAll());
        return "index";
    }

    @GetMapping("/settings")
    public String settingPage(Model model) {
        model.addAttribute("members", customerService.findAll());
        return "settings";
    }

    @GetMapping(path = "/cart", produces = "text/html")
    public String cart() {
        return "cart";
    }
}
