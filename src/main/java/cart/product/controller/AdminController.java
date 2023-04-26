package cart.product.controller;

import cart.product.dto.ProductDto;
import cart.product.service.ProductListService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    
    private final ProductListService productListService;
    
    public AdminController(final ProductListService productListService) {
        this.productListService = productListService;
    }
    
    @GetMapping("/product/list")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> display() {
        return this.productListService.display();
    }
    
    @PostMapping("/product/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody final ProductDto productDto) {
        this.productListService.create(productDto);
    }
    
    @PutMapping("/product/update/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto update(@PathVariable final long id, @RequestBody final ProductDto productDto) {
        return this.productListService.update(id, productDto);
    }
    
    @DeleteMapping("/product/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable final long id) {
        this.productListService.delete(id);
    }
}
