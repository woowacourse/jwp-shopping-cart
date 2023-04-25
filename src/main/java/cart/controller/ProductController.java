package cart.controller;

import cart.dto.ProductCreateRequest;
import cart.dto.ProductDto;
import cart.dto.ProductUpdateRequest;
import cart.dto.Response;
import cart.dto.ResultResponse;
import cart.dto.SimpleResponse;
import cart.service.ProductService;
import java.net.URI;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Response> createProduct(@RequestBody @Valid ProductCreateRequest request) {
        ProductDto productDto = productService.createProduct(request.getName(), request.getPrice(), request.getImageUrl());
        return ResponseEntity
                .created(URI.create("/products/" + productDto.getId()))
                .body(new ResultResponse<>("201", "상품이 생성되었습니다.", productDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity
                .ok(new SimpleResponse("200", "상품이 제거되었습니다."));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Response> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductUpdateRequest request) {
        productService.updateProductById(id, request.getName(), request.getPrice(), request.getImageUrl());
        return ResponseEntity
                .ok(new ResultResponse<>("200", "상품이 수정되었습니다.", request));
    }
}
