package cart.admin.controller;

import cart.catalog.service.ProductListService;
import cart.product.dto.RequestProductDto;
import cart.product.dto.ResponseProductDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final ProductListService productListService;

    public AdminController(final ProductListService productListService) {
        this.productListService = productListService;
    }

    @GetMapping("/product/list")
    @ResponseStatus(HttpStatus.OK)
    public List<ResponseProductDto> display() {
        return this.productListService.display();
    }

    @PostMapping("/product/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody final RequestProductDto requestProductDto) {
        this.productListService.create(requestProductDto);
    }

    @PutMapping("/product/update/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseProductDto update(@PathVariable final long id,
                                     @RequestBody final RequestProductDto requestProductDto) {
        return this.productListService.update(id, requestProductDto);
    }

    @DeleteMapping("/product/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable final long id) {
        this.productListService.delete(id);
    }
}
