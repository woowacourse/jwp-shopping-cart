package cart.controller.api;

import cart.dto.ProductCategoryDto;
import cart.dto.request.ProductRequestDto;
import cart.dto.response.ProductResponseDto;
import cart.dtomapper.ProductResponseDtoMapper;
import cart.service.ProductService;
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

@RestController
@RequestMapping(path = "/products")
public final class ProductApiController {

    private final ProductService productService;

    public ProductApiController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> findProducts() {
        final List<ProductCategoryDto> productCategoryDtos = productService.findAll();
        return ResponseEntity.ok(ProductResponseDtoMapper.mapTo(productCategoryDtos));
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody ProductRequestDto productRequestDto) {
        final Long registeredProductId = productService.register(productRequestDto);
        return ResponseEntity.created(URI.create("/products/" + registeredProductId)).build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Void> update(
        @PathVariable(name = "id") Long productId,
        @Valid @RequestBody ProductRequestDto productRequestDto
    ) {
        productService.update(productId, productRequestDto);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Long productId) {
        productService.delete(productId);
        return ResponseEntity.noContent().build();
    }
}
