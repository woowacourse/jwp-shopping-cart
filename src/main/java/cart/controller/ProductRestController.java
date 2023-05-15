package cart.controller;

import cart.dto.product.RequestProductDto;
import cart.dto.product.ResponseProductDto;
import cart.service.ProductService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductRestController {
    
    private final ProductService productService;
    
    public ProductRestController(final ProductService productService) {
        this.productService = productService;
    }
    
    @GetMapping("/products")
    @ResponseStatus(HttpStatus.OK)
    public List<ResponseProductDto> display() {
        return productService.display();
    }
    
    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid final RequestProductDto requestProductDto) {
        productService.create(requestProductDto);
    }
    
    @PutMapping("/products/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseProductDto update(@PathVariable final long id,
            @RequestBody @Valid final RequestProductDto requestProductDto) {
        return productService.update(id, requestProductDto);
    }
    
    @DeleteMapping("/products/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable final long id) {
        productService.delete(id);
    }
}
