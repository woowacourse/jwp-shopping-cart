package cart.controller;

import cart.dto.ProductAddRequestDto;
import cart.dto.ProductModifyRequestDto;
import cart.service.ProductService;
import java.net.URI;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("")
    public ResponseEntity<Void> addProduct(@RequestBody @Valid ProductAddRequestDto productAddRequestDto) {
        productService.add(productAddRequestDto);
        return ResponseEntity.created(URI.create("/admin")).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> modifyProduct(@PathVariable int id,
                                              @RequestBody @Valid ProductModifyRequestDto productModifyRequestDto) {
        productService.modifyById(id, productModifyRequestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeProduct(@PathVariable int id) {
        productService.removeById(id);
        return ResponseEntity.ok().build();
    }

}
