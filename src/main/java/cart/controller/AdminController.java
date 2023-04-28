package cart.controller;

import cart.controller.dto.request.ProductCreateRequest;
import cart.controller.dto.request.ProductUpdateRequest;
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

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;

    public AdminController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String admin(Model model) {
        model.addAttribute("products", productService.findAll());
        return "admin";
    }

    @PostMapping
    public void createProduct(@RequestBody @Valid final ProductCreateRequest request) {
        productService.create(request);
    }

    @PutMapping("{id}")
    public String update(
            @PathVariable final Long id,
            @RequestBody @Valid final ProductUpdateRequest request
    ) {
        productService.update(id, request);
        return "admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable final Long id) {
        productService.delete(id);
        return "admin";
    }

}
