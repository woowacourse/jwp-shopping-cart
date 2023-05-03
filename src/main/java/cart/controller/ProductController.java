package cart.controller;

import cart.dto.ProductDto;
import cart.dto.ProductRequestDto;
import cart.dto.ProductResponseDto;
import cart.service.JwpCartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final JwpCartService jwpCartService;

    public ProductController(JwpCartService jwpCartService) {
        this.jwpCartService = jwpCartService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductResponseDto> addProduct(@RequestBody @Valid ProductRequestDto productRequestDto) {
        ProductDto productdto = jwpCartService.add(productRequestDto);
        ProductResponseDto response = ProductResponseDto.fromProductDto(productdto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(URI.create("/products/" + response.getId()))
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable("id") Long id,
                                                            @RequestBody @Valid ProductRequestDto productRequestDto) {
        ProductDto productDto = jwpCartService.updateById(productRequestDto, id);
        ProductResponseDto response = ProductResponseDto.fromProductDto(productDto);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        jwpCartService.deleteById(id);
        return ResponseEntity.ok()
                .build();
    }
}
