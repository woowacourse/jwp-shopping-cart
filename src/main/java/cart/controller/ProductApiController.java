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
    public ResponseEntity<String> insert(@Valid @RequestBody ProductDto productDto) {
        productService.insert(productDto);
        return ResponseEntity.ok().body("ok");
    }

    @PostMapping("/product/update/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @Valid @RequestBody ProductDto productDto) {
        productService.update(id, productDto);
        return ResponseEntity.ok().body(String.valueOf(id));
    }

    @DeleteMapping("/product/delete/{id}")
    public void delete(@PathVariable int id) {
        productService.delete(id);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult().getFieldError().getField() + "을 다시 입력해주세요.";
        return ResponseEntity.badRequest().body(errorMessage);
    }

}
