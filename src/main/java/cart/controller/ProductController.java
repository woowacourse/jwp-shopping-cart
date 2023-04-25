package cart.controller;

import cart.dto.CreateProductRequest;
import cart.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(path = "/product")
    public ResponseEntity<String> createProduct(@RequestBody @Valid CreateProductRequest request) {
        productService.save(request.getName(), request.getPrice(), request.getImage());
        return ResponseEntity.ok().body("상품 생성 성공.");
    }
}
