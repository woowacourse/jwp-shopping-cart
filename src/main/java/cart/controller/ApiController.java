package cart.controller;

import cart.dto.ProductRequest;
import cart.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ApiController {

    private final ProductService productService;

    public ApiController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/addProduct") // 야 스프링. 클라이언트의 POST/GET 요청에 대해 다음과 같이 처리해라
    public ResponseEntity<String> addProduct(@RequestBody ProductRequest productRequest) {
        productService.add(productRequest.getName(), productRequest.getImage(), productRequest.getPrice());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/modifyProduct/{id}") // 야 스프링. 클라이언트의 POST/GET 요청에 대해 다음과 같이 처리해라
    public ResponseEntity<String> modifyProduct(
            @PathVariable("id") Integer id,
            @RequestBody ProductRequest productRequest) {
        productService.modify(id, productRequest.getName(), productRequest.getImage(), productRequest.getPrice());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/deleteProduct/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Integer id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }
}
