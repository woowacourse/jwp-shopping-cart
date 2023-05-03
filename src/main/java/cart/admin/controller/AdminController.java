package cart.admin.controller;

import cart.catalog.service.ProductCatalogService;
import cart.product.dto.RequestProductDto;
import cart.product.dto.ResponseProductDto;
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
    
    private final ProductCatalogService productCatalogService;
    
    public AdminController(final ProductCatalogService productCatalogService) {
        this.productCatalogService = productCatalogService;
    }
    
    @GetMapping("/products")
    @ResponseStatus(HttpStatus.OK)
    public List<ResponseProductDto> display() {
        return this.productCatalogService.display();
    }
    
    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody final RequestProductDto requestProductDto) {
        this.productCatalogService.create(requestProductDto);
    }
    
    @PutMapping("/products/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseProductDto update(@PathVariable final long id,
            @RequestBody final RequestProductDto requestProductDto) {
        return this.productCatalogService.update(id, requestProductDto);
    }
    
    @DeleteMapping("/products/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable final long id) {
        this.productCatalogService.delete(id);
    }
}
