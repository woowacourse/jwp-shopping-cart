package cart.controller;

import cart.dto.request.ProductCreateRequest;
import cart.dto.ProductDto;
import cart.dto.request.ProductUpdateRequest;
import cart.dto.response.Response;
import cart.dto.response.ResultResponse;
import cart.dto.response.SimpleResponse;
import cart.service.ProductService;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ProductApiController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Response> createProduct(@RequestBody @Valid ProductCreateRequest request) {
        ProductDto productDto = productService.createProduct(request.getName(), request.getPrice(),
                request.getImageUrl());
        return ResponseEntity
                .created(URI.create("/products/" + productDto.getId()))
                .body(ResultResponse.created("상품이 생성되었습니다.", productDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity
                .ok(SimpleResponse.ok("상품이 삭제되었습니다."));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Response> updateProduct(@PathVariable Long id,
                                                  @RequestBody @Valid ProductUpdateRequest request) {
        ProductDto productDto = productService.updateProductById(id, request.getName(), request.getPrice(),
                request.getImageUrl());
        return ResponseEntity
                .ok(ResultResponse.ok("상품이 수정되었습니다.", productDto));
    }

    @GetMapping
    public ResponseEntity<Response> findAllProducts() {
        List<ProductDto> allProducts = productService.findAllProducts();
        return ResponseEntity.ok()
                .body(ResultResponse.ok("총 " + allProducts.size() + "개의 상품이 조회되었습니다.", allProducts));
    }
}
