package cart.product.controller;

import cart.product.dto.RequestProductDto;
import cart.product.dto.ResponseProductDto;
import cart.product.service.ProductListService;
import java.util.List;
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
    
    private final ProductListService productListService;
    
    public ProductRestController(final ProductListService productListService) {
        this.productListService = productListService;
    }
    
    @GetMapping("/products")
    @ResponseStatus(HttpStatus.OK)
    public List<ResponseProductDto> display() {
        return productListService.display();
    }
    
    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody final RequestProductDto requestProductDto) {
        productListService.create(requestProductDto);
    }
    
    @PutMapping("/products/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseProductDto update(@PathVariable final long id,
            @RequestBody final RequestProductDto requestProductDto) {
        return productListService.update(id, requestProductDto);
    }
    
    @DeleteMapping("/products/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable final long id) {
        productListService.delete(id);
    }
}
