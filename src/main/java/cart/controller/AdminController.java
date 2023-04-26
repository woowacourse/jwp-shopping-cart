package cart.controller;

import cart.dto.ProductRequestDto;
import cart.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public String adminPage(Model model) {
        model.addAttribute("products", productService.findAll());
        return "admin";
    }

    @PostMapping("/products")
    public String addProduct(@RequestBody ProductRequestDto productRequestDto) {
        productService.add(productRequestDto);
        return "admin";
    }

    @PutMapping("/products/{id}")
    public String modifyProduct(@PathVariable int id, @RequestBody ProductRequestDto productRequestDto) {
        productService.modifyById(id, productRequestDto);
        return "admin";
    }

    @DeleteMapping("/products/{id}")
    public String removeProduct(@PathVariable int id) {
        productService.removeById(id);
        return "admin";
    }

}
