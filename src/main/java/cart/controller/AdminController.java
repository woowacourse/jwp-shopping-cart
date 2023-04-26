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
    public String admin(Model model) {
        model.addAttribute("products", productService.findAll());
        return "admin";
    }

    @PostMapping("/product")
    public String saveProduct(@RequestBody ProductRequestDto productRequestDto) {
        productService.save(productRequestDto);
        return "admin";
    }

    @PutMapping("/product/{id}")
    public String updateProduct(@PathVariable int id, @RequestBody ProductRequestDto productRequestDto) {
        productService.updateById(id, productRequestDto);
        return "admin";
    }

    @DeleteMapping("/product/{id}")
    public String deleteProduct(@PathVariable int id) {
        productService.deleteById(id);
        return "admin";
    }

}
