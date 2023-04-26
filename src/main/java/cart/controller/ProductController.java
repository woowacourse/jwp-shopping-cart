package cart.controller;

import cart.dto.ProductRequest;
import cart.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping()
    public ResponseEntity<String> productAdd(@Validated @RequestBody ProductRequest productRequest) {
        productService.save(productRequest);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> productModify(@RequestBody ProductRequest productRequest, @PathVariable int id) {
        productService.update(productRequest, id);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> productRemove(@PathVariable int id) {
        productService.delete(id);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleServerException(Exception e) {
        return new ResponseEntity<>("server error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
