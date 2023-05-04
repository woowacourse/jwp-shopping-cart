package cart.presentation.controller;


import cart.business.service.ProductService;
import cart.presentation.adapter.DomainConverter;
import cart.presentation.dto.ProductDto;
import cart.presentation.dto.ProductIdDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Void> productCreate(@RequestBody @Valid ProductDto request) {
        productService.create(DomainConverter.toProductWithoutId(request));

        return ResponseEntity.status(HttpStatus.CREATED).build();
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
    public ResponseEntity<Void> productUpdate(@RequestBody @Valid ProductDto request) {
        productService.update(DomainConverter.toProductWithId(request));

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> productDelete(@RequestBody @Valid ProductIdDto request) {
        productService.delete(request.getId());

        return ResponseEntity.noContent().build();
    }
}
