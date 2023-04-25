package cart.controller;

import cart.dto.ProductResponse;
import cart.service.ProductManagementService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    private final ProductManagementService managementService;

    public MainController(final ProductManagementService managementService) {
        this.managementService = managementService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("products", ProductResponse.from(managementService.findAll()));
        return "index";
    }
}
