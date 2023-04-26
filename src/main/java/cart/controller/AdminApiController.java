package cart.controller;

import cart.controller.dto.ProductRequestDto;
import cart.service.dto.ProductDto;
import cart.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminApiController {

    private final ProductService productService;

    public AdminApiController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/product")
    public ResponseEntity<Void> insertProduct(@Valid @RequestBody final ProductRequestDto productRequestDto) {
        productService.insertProduct(new ProductDto.Builder()
                .name(productRequestDto.getName())
                .price(productRequestDto.getPrice())
                .image(productRequestDto.getImage())
                .build());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable final Long id,@Valid @RequestBody final ProductRequestDto productRequestDto) {
        productService.updateById(new ProductDto.Builder()
                .id(id)
                .name(productRequestDto.getName())
                .price(productRequestDto.getPrice())
                .image(productRequestDto.getImage())
                .build());

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable final Long id) {
        productService.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
