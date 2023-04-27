package cart.controller;

import cart.domain.Product;
import cart.dto.ProductDto;
import cart.request.ProductRequest;
import cart.service.ProductService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String indexPage(final Model model) {
        final List<Product> products = productService.findAll();

        model.addAttribute("products", products);

        return "index";
    }

    @GetMapping("/admin")
    public String adminPage(final Model model) {
        final List<Product> products = productService.findAll();

        model.addAttribute("products", products);

        return "admin";
    }

    @PostMapping("/admin")
    public void createProduct(@RequestBody @Valid final ProductRequest productRequest) {
        final ProductDto productDto = new ProductDto(
                productRequest.getName(),
                productRequest.getPrice(),
                productRequest.getImageUrl()
        );

        productService.register(productDto);
    }

    @PutMapping("/admin/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateProduct(@PathVariable final long id, final @RequestBody @Valid ProductRequest productRequest) {
        productService.updateProduct(id, new ProductDto(productRequest));
    }

    @DeleteMapping("/admin/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteProduct(@PathVariable final long id) {
        productService.deleteProduct(id);
    }
}
