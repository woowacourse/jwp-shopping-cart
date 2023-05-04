package cart.controller;

import cart.dto.request.ProductRequestDto;
import cart.dto.response.ProductResponseDto;
import cart.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Product", description = "상품 API Document")
@RequestMapping("/products")
@RestController
public final class ProductApiController {

    private final ProductService productService;

    public ProductApiController(final ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "상품 목록 조회 API", description = "전체 상품 목록을 조회한다.")
    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> findProducts() {
        final List<ProductResponseDto> response = productService.findProducts();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "상품 등록 API", description = "상품 정보를 바탕으로 상품을 등록한다.")
    @PostMapping
    public ResponseEntity<Void> registerProduct(@Valid @RequestBody ProductRequestDto productRequestDto) {
        final Long productId = productService.registerProduct(productRequestDto);
        return ResponseEntity.created(URI.create("/products/" + productId)).build();
    }

    @Operation(summary = "상품 수정 API", description = "상품 정보를 바탕으로 상품을 수정한다.")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(
            @PathVariable(name = "id") Long productId,
            @Valid @RequestBody ProductRequestDto productRequestDto
    ) {
        productService.updateProduct(productId, productRequestDto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "상품 삭제 API", description = "해당 상품을 삭제한다.")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> removeProduct(@PathVariable(name = "id") Long productId) {
        productService.removeProduct(productId);
        return ResponseEntity.noContent().build();
    }
}

