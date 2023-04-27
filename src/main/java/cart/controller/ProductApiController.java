package cart.controller;

import cart.dto.ProductDto;
import cart.service.ProductService;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductApiController {

    private final ProductService productService;

    public ProductApiController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/product/insert")
    public ResponseEntity<Integer> insert(@Valid @RequestBody ProductDto productDto) {
        Integer savedId = productService.insert(productDto);
        return ResponseEntity.ok().body(savedId);
    }

    @PostMapping("/product/update/{id}")
    public ResponseEntity<Integer> update(@PathVariable int id, @Valid @RequestBody ProductDto productDto) {
        productService.update(id, productDto);
        return ResponseEntity.ok().body(id);
    }

    @DeleteMapping("/product/delete/{id}")
    public Integer delete(@PathVariable int id) {
        productService.delete(id);
        return id;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return ResponseEntity.badRequest()
                .body(exception.getBindingResult().getFieldErrors().get(0).getDefaultMessage());
    }

}
