package cart.controller;

import cart.dto.ProductDto;
import cart.service.ProductService;
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
    public void insert(@RequestBody ProductDto productDto) {
        productService.insert(productDto);
    }

}
