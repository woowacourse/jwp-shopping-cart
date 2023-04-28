package cart.controller;

import cart.service.ProductCommandService;
import cart.service.dto.ProductModifyRequest;
import cart.service.dto.ProductRegisterRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductCommandService productCommandService;

    public ProductController(final ProductCommandService productCommandService) {
        this.productCommandService = productCommandService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registerProduct(@Validated @RequestBody final ProductRegisterRequest productRegisterRequest) {
        productCommandService.registerProduct(productRegisterRequest);
    }

    @PatchMapping("/{product-id}")
    public String modifyProduct(
            @PathVariable("product-id") final Long productId,
            @Validated @RequestBody final ProductModifyRequest productModifyRequest
    ) {
        productCommandService.modifyProduct(productId, productModifyRequest);

        return "admin";
    }

    @DeleteMapping("/{product-id}")
    public String deleteProduct(@PathVariable("product-id") final Long productId) {
        productCommandService.deleteProduct(productId);

        return "admin";
    }
}
