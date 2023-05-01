package cart.presentation;


import cart.application.ProductCRUDApplication;
import cart.presentation.dto.ProductDto;
import cart.presentation.dto.ProductIdDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/product")
public class ProductController {

    private final ProductCRUDApplication productCRUDApplication;

    public ProductController(ProductCRUDApplication productCRUDApplication) {
        this.productCRUDApplication = productCRUDApplication;
    }

    @PostMapping
    public void productCreate(@RequestBody @Valid ProductDto request) {
        productCRUDApplication.create(request);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> productRead() {
        List<ProductDto> response = productCRUDApplication.readAll();
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public void productUpdate(@RequestBody @Valid ProductDto request) {
        productCRUDApplication.update(request);
    }

    @DeleteMapping
    public void productDelete(@RequestBody @Valid ProductIdDto request) {
        productCRUDApplication.delete(request);
    }
}
