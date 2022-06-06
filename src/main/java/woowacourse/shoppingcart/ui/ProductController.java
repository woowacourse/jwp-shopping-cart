package woowacourse.shoppingcart.ui;

import java.net.URI;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.shoppingcart.application.ProductService;
import woowacourse.shoppingcart.application.dto.ProductRequest;
import woowacourse.shoppingcart.application.dto.ProductResponse;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Void> add(@RequestBody ProductRequest productRequest) {
        Long productId = productService.save(productRequest);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + productId)
                .build().toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{productId}")
    public ProductResponse findProduct(@PathVariable Long productId) {
        return productService.findById(productId);
    }

    @GetMapping
    public List<ProductResponse> findAll() {
        return productService.findAll();
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long productId) {
        productService.delete(productId);
    }
}
