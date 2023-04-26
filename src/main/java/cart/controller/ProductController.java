package cart.controller;

import cart.dto.ProductSaveRequestDto;
import cart.dto.ProductUpdateRequestDto;
import cart.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/products")
    public void save(@RequestBody final ProductSaveRequestDto request) {
        productService.save(request);
    }

    @GetMapping("/")
    public String index(final Model model) {
        model.addAttribute("products", productService.findAll());
        return "index";
    }

    @GetMapping("/admin")
    public String admin(final Model model) {
        model.addAttribute("products", productService.findAll());
        return "admin";
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/products/{id}")
    public void update(@PathVariable final Long id, @RequestBody ProductUpdateRequestDto request) {
        productService.update(id, request);
    }
}
