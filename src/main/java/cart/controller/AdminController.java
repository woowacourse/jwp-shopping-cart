package cart.controller;

import cart.controller.dto.ProductDto;
import cart.persistence.entity.ProductCategory;
import cart.service.ShoppingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ShoppingService shoppingService;

    public AdminController(final ShoppingService shoppingService) {
        this.shoppingService = shoppingService;
    }

    @GetMapping
    public String getProducts(final Model model) {
        final List<ProductDto> products = shoppingService.getProducts();
        model.addAttribute("products", products);
        return "admin";
    }

    @PostMapping
    public String addProduct(@RequestBody @Valid final ProductDto productDto) {
        shoppingService.save(productDto);
        return "redirect:/admin";
    }

    @PutMapping("/{id}")
    public String updateProduct(
            @PathVariable final Long id,
            @RequestBody @Valid final ProductDto productDto) {
        shoppingService.update(id, productDto);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable final Long id) {
        shoppingService.delete(id);
        return "redirect:/admin";
    }

    @ModelAttribute("categorys")
    public List<ProductCategory> productCategories() {
        return List.of(ProductCategory.values());
    }
}
