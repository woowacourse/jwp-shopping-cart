package woowacourse.shoppingcart.ui;

import org.springframework.web.bind.annotation.*;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.dto.ProductsResponse;
import woowacourse.shoppingcart.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ProductsResponse searchPagedProducts(@RequestParam int size, @RequestParam int page) {
        return productService.findProducts(page, size);
    }

    @GetMapping("/{productId}")
    public ProductResponse searchOneProducts(@PathVariable Long productId) {
        return productService.findProductById(productId);
    }
}
