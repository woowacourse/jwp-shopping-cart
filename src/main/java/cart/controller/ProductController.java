package cart.controller;

import cart.dto.product.ProductDto;
import cart.dto.product.ProductRequestDto;
import cart.dto.product.ProductResponseDto;
import cart.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductResponseDto> addProduct(@RequestBody @Valid ProductRequestDto requestDto) {
        ProductDto dto = productService.add(requestDto);
        ProductResponseDto response = ProductResponseDto.fromDto(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(URI.create("/products/" + response.getId()))
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable("id") Long id,
                                                            @RequestBody @Valid ProductRequestDto requestDto) {
        ProductDto dto = productService.updateById(requestDto, id);
        ProductResponseDto response = ProductResponseDto.fromDto(dto);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        productService.deleteById(id);
        return ResponseEntity.ok()
                .build();
    }
}
