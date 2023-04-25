package cart.controller;

import cart.dto.ProductRequestDto;
import cart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;

    public AdminController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public String admin() {
        return "redirect:/admin/product";
    }

    @GetMapping("/product")
    public String getProduct(Model model) {
        model.addAttribute("products", productService.findAll());
        return "admin";
    }

    @PostMapping("/product")
    public String saveProduct(@RequestBody ProductRequestDto productRequestDto) {
        productService.save(productRequestDto);
        return "redirect:/admin/product";
    }

}
