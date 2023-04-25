package cart.controller;

import cart.service.ProductService;
import cart.service.dto.ProductModifyRequest;
import cart.service.dto.ProductRegisterRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

//TODO : URL 에 따른 컨트롤러 분리할 것
@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registerProduct(@RequestBody final ProductRegisterRequest productRegisterRequest) {
        productService.registerProduct(productRegisterRequest);
    }

    @PatchMapping("/{product-id}")
    public String modifyProduct(
            @PathVariable("product-id") final Long productId,
            @RequestBody final ProductModifyRequest productModifyRequest) {
        productService.modifyProduct(productId, productModifyRequest);

        return "admin";
    }

    @DeleteMapping("/{product-id}")
    public String deleteProduct(@PathVariable("product-id") final Long productId) {
        productService.deleteProduct(productId);

        return "admin";
    }
}
