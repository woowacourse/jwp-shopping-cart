package cart.controller;

import cart.controller.dto.ProductRequestDto;
import cart.controller.dto.ProductResponseDto;
import cart.service.ProductService;
import cart.service.dto.ProductDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductsApiController {

    private final ProductService productService;

    public ProductsApiController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity insertProduct(@Valid @RequestBody final ProductRequestDto productRequestDto) {
        productService.insertProduct(new ProductDto.Builder()
                .name(productRequestDto.getName())
                .price(productRequestDto.getPrice())
                .image(productRequestDto.getImage())
                .build());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("")
    public ResponseEntity<List<ProductResponseDto>> readAllProducts() {
        List<ProductResponseDto> products = productService.findAll()
                .stream()
                .map(productDto -> new ProductResponseDto(
                        productDto.getId(),
                        productDto.getName(),
                        productDto.getPrice(),
                        productDto.getImage()
                ))
                .collect(Collectors.toUnmodifiableList());
        return ResponseEntity
                .ok()
                .body(products);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateProduct(@PathVariable final Long id, @Valid @RequestBody final ProductRequestDto productRequestDto) {
        productService.updateById(new ProductDto.Builder()
                .id(id)
                .name(productRequestDto.getName())
                .price(productRequestDto.getPrice())
                .image(productRequestDto.getImage())
                .build());
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable final Long id) {
        productService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
