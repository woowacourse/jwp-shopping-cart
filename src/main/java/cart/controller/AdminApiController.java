package cart.controller;

import cart.controller.dto.ProductRequest;
import cart.controller.dto.UpdateRequest;
import cart.dto.ProductDto;
import cart.service.ProductService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminApiController {

    private final ProductService productService;

    public AdminApiController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/product")
    public void insertProduct(@RequestBody final ProductRequest productRequest) {
        productService.insertProduct(new ProductDto.Builder()
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .image(productRequest.getImage())
                .build());
    }

    @PutMapping("/product")
    public void updateProduct(@RequestBody final UpdateRequest updateRequest) {
        productService.updateById(new ProductDto.Builder()
                .id(updateRequest.getId())
                .name(updateRequest.getName())
                .price(updateRequest.getPrice())
                .image(updateRequest.getImage())
                .build());
    }

    @DeleteMapping("/product")
    public void deleteProduct(@RequestBody final Map<String, Long> id) {
        productService.deleteById(id.get("id"));
    }
}
