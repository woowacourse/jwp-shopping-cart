package cart.controller;

import cart.dto.ProductDto;
import cart.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductApiController {

    private final ProductService productService;

    public ProductApiController(final ProductService productService) {
        this.productService = productService;
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public String insert(@Valid @RequestBody ProductDto productDto) {
        productService.insert(productDto);

        return "ok";
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    public String update(@PathVariable int id, @Valid @RequestBody ProductDto productDto) {
        productService.update(id, productDto);

        return "ok";
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        productService.delete(id);

        return "ok";
    }

}
