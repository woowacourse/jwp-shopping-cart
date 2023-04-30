package cart.controller.api;

import cart.dto.product.ProductDto;
import cart.dto.request.product.ProductCreateRequest;
import cart.dto.request.product.ProductUpdateRequest;
import cart.dto.response.Response;
import cart.dto.response.ResultResponse;
import cart.dto.response.SimpleResponse;
import cart.service.ProductService;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductApiController {
    private static final Logger log = LoggerFactory.getLogger(ProductApiController.class);
    private final ProductService productService;

    public ProductApiController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Response> createProduct(@RequestBody @Valid ProductCreateRequest request) {
        ProductDto productDto = productService.createProduct(request.getName(), request.getPrice(),
                request.getImageUrl());
        log.info("상품이 생성되었습니다. 상품 ID = {}", productDto.getProductId());
        return ResponseEntity.created(URI.create("/products/" + productDto.getProductId()))
                .body(ResultResponse.created("상품이 생성되었습니다.", productDto));
    }

    @DeleteMapping("/{productId:[1-9]\\d*}")
    public ResponseEntity<Response> deleteProduct(@PathVariable Long productId) {
        productService.deleteById(productId);
        log.info("상품이 삭제되었습니다. 상품 ID = {}", productId);
        return ResponseEntity.ok()
                .body(SimpleResponse.ok("상품이 삭제되었습니다."));
    }

    @PatchMapping("/{productId:[1-9]\\d*}")
    public ResponseEntity<Response> updateProduct(@PathVariable Long productId,
                                                  @RequestBody @Valid ProductUpdateRequest request) {
        ProductDto productDto = productService.updateProductById(productId, request.getName(), request.getPrice(),
                request.getImageUrl());
        log.info("상품이 수정되었습니다. 상품 ID = {}", productId);
        return ResponseEntity.ok()
                .body(ResultResponse.ok("상품이 수정되었습니다.", productDto));
    }

    @GetMapping
    public ResponseEntity<Response> findAllProducts() {
        List<ProductDto> allProducts = productService.findAllProducts();
        return ResponseEntity.ok()
                .body(ResultResponse.ok(allProducts.size() + "개의 상품이 조회되었습니다.", allProducts));
    }
}
