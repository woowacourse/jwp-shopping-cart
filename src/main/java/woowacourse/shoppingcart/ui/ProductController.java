package woowacourse.shoppingcart.ui;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.shoppingcart.application.ProductService;
import woowacourse.shoppingcart.dto.product.PageRequest;
import woowacourse.shoppingcart.dto.product.ProductResponse;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> products(@ModelAttribute @Valid PageRequest pageRequest) {
        List<ProductResponse> responses = productService.findByPage(pageRequest).stream()
                .map(ProductResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok()
                .header("x-total-count", String.valueOf(productService.findTotalCount()))
                .body(responses);
    }
}
