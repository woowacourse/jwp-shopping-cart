package cart.controller;

import cart.dto.ProductRequestDto;
import cart.entity.ProductEntity;
import cart.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid ProductRequestDto productRequestDto) {
        productService.create(productRequestDto);
        return ResponseEntity.created(URI.create("/admin")).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody final ProductRequestDto productRequestDto, @PathVariable final int id){
        productService.update(productRequestDto, id);
        return ResponseEntity.created(URI.create("/admin")).build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Void> handle() {
        return ResponseEntity.badRequest().build();
    }

}
