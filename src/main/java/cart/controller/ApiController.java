package cart.controller;

import cart.controller.dto.ProductRequest;
import cart.controller.dto.UpdateRequest;
import cart.dto.ProductDto;
import cart.service.ProductService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ApiController {

    private final ProductService productService;

    public ApiController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/product")
    public void insertProduct(@RequestBody final ProductRequest productRequest) {
        productService.insertProduct(ProductDto.createProductDto(
                productRequest.getName(),
                productRequest.getPrice(),
                productRequest.getImage()));
    }

    @PutMapping("/product")
    public void updateProduct(@RequestBody final UpdateRequest updateRequest) {
        productService.updateById(ProductDto.createProductDto(
                updateRequest.getId(),
                updateRequest.getName(),
                updateRequest.getPrice(),
                updateRequest.getImage()));
    }

    @DeleteMapping("/product")
    public void deleteProduct(@RequestBody final Map<String, Long> id) {
        productService.deleteById(id.get("id"));
    }
}
