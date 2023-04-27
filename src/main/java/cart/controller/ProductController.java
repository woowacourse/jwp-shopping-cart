package cart.controller;

import cart.service.ProductService;
import cart.service.dto.ProductModifyRequest;
import cart.service.dto.ProductRegisterRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registerProduct(@Validated @RequestBody final ProductRegisterRequest productRegisterRequest) {
        productService.registerProduct(productRegisterRequest);
    }

    @PutMapping("/{product-id}")
    public String modifyProduct(
            @PathVariable("product-id") final Long productId,
            @Validated @RequestBody final ProductModifyRequest productModifyRequest
    ) {
        productService.modifyProduct(productId, productModifyRequest);

        return "admin";
    }

    @DeleteMapping("/{product-id}")
    public String deleteProduct(@PathVariable("product-id") final Long productId) {
        productService.deleteProduct(productId);

        return "admin";
    }
}
