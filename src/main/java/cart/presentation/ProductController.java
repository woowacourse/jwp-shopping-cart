package cart.presentation;


import cart.application.ProductCRUDApplication;
import cart.presentation.dto.ProductDto;
import cart.presentation.dto.ProductIdDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/product")
public class ProductController {

    private final ProductCRUDApplication productCRUDApplication;

    public ProductController(ProductCRUDApplication productCRUDApplication) {
        this.productCRUDApplication = productCRUDApplication;
    }

    @PostMapping(path = "/create")
    public void productCreate(@RequestBody ProductDto request) {
        productCRUDApplication.create(request);
    }

    @GetMapping(path = "/read")
    public ResponseEntity<List<ProductDto>> productRead() {
        List<ProductDto> response = productCRUDApplication.readAll();
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/update")
    public void productUpdate(@RequestBody ProductDto request) {
        productCRUDApplication.update(request);
    }

    @DeleteMapping(path = "/delete")
    public void productDelete(@RequestBody ProductIdDto request) {
        productCRUDApplication.delete(request);
    }
}
