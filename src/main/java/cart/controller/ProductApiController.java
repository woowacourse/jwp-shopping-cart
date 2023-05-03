package cart.controller;

import cart.dto.ProductDto;
import cart.service.ProductService;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ProductApiController {

    private final ProductService productService;

    public ProductApiController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    public String insert(@Valid @RequestBody ProductDto productDto, Errors errors) {
        if (errors.hasErrors()) {
            return errors.getFieldError().getField();
        }
        productService.insert(productDto);

        return "ok";
    }

    @PatchMapping("/products/{id}")
    public String update(@PathVariable int id, @Valid @RequestBody ProductDto productDto) {
        productService.update(id, productDto);

        return "ok";
    }

    @DeleteMapping("/products/{id}")
    public String delete(@PathVariable int id) {
        productService.delete(id);

        return "ok";
    }

}
