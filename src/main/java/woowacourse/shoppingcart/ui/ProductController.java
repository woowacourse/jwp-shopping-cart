package woowacourse.shoppingcart.ui;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import woowacourse.shoppingcart.application.ProductService;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.AddProductRequest;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Product> products() {
        return productService.findProducts();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@Validated @RequestBody final AddProductRequest request) {
        productService.addProduct(request);
    }

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public Product product(@PathVariable final Long productId) {
        return productService.findProductById(productId);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable final Long productId) {
        productService.deleteProductById(productId);
    }
}
