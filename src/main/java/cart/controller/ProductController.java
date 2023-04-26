package cart.controller;

import cart.dto.ProductDto;
import cart.dto.ProductRequest;
import cart.service.ProductService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<ProductDto> products = productService.findAll();
        model.addAttribute("products", products);
        return "index";
    }

    @PostMapping("/admin")
    public ResponseEntity<String> productAdd(@Validated @RequestBody ProductRequest productRequest) {
        productService.save(productRequest);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @GetMapping("/admin")
    public String productList(Model model) {
        List<ProductDto> products = productService.findAll();
        model.addAttribute("products", products);
        return "admin";
    }

    @PostMapping("/admin/{id}")
    public ResponseEntity<String> productModify(@RequestBody ProductRequest productRequest, @PathVariable int id) {
        productService.update(productRequest, id);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<String> productRemove(@PathVariable int id) {
        productService.delete(id);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleServerException(Exception e) {
        return new ResponseEntity<>("server error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
