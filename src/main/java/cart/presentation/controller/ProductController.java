package cart.presentation.controller;


import cart.business.service.ProductService;
import cart.presentation.adapter.DomainConverter;
import cart.presentation.dto.ProductDto;
import cart.presentation.dto.ProductIdDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public void productCreate(@RequestBody @Valid ProductDto request) {
        productService.create(DomainConverter.toProductWithoutId(request));
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> productRead() {
        List<ProductDto> response = productService.readAll()
                .stream()
                .map(DomainConverter::toProductDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PutMapping
    public void productUpdate(@RequestBody @Valid ProductDto request) {
        productService.update(DomainConverter.toProductWithId(request));
    }

    @DeleteMapping
    public void productDelete(@RequestBody @Valid ProductIdDto request) {
        productService.delete(request.getId());
    }
}
