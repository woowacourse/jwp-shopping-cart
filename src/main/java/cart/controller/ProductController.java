package cart.controller;

import cart.dto.ProductCreateDto;
import cart.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.net.URI;


@Controller
public class ProductController {
    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/admin")
    public ResponseEntity<Void> create(@RequestBody @Valid ProductCreateDto productCreateDto) {
        productService.create(productCreateDto);
        return ResponseEntity.created(URI.create("/admin")).build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Void> handle() {
        return ResponseEntity.badRequest().build();
    }

}
