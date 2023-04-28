package cart.controller;

import cart.domain.Product;
import cart.dto.ProductRequest.AddDto;
import cart.dto.ProductRequest.UpdateDto;
import cart.service.ProductService;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void create(@Valid @RequestBody AddDto productDto) {
        productService.add(new Product(
                productDto.getName(),
                productDto.getImageUrl(),
                productDto.getPrice()
        ));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping
    public void update(@Valid @RequestBody UpdateDto productDto) {
        productService.update(new Product(
                productDto.getId(),
                productDto.getName(),
                productDto.getImageUrl(),
                productDto.getPrice()
        ));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        productService.deleteById(id);
    }
}
