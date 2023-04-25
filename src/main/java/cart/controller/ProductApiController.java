package cart.controller;

import cart.dto.ProductDto;
import cart.service.ProductService;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductApiController {

    private final ProductService productService;

    public ProductApiController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/product/insert")
    public ResponseEntity<String> insert(@Valid @RequestBody ProductDto productDto, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getFieldError().getField());
        }
        productService.insert(productDto);
        return ResponseEntity.ok().body("ok");
    }

    @PostMapping("/product/update/{id}")
    public void update(@PathVariable int id, @Valid @RequestBody ProductDto productDto) {
        productService.update(id, productDto);
    }

    @DeleteMapping("/product/delete/{id}")
    public void delete(@PathVariable int id) {
        productService.delete(id);
    }
}
