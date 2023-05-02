package cart.controller;

import cart.dto.ProductDto;
import cart.dto.ProductSaveRequestDto;
import cart.dto.ProductUpdateRequestDto;
import cart.service.ProductService;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

    //todo (질문1): (PageController)에서 admin 페이지를 보여주는 거랑 중복 코드인 것같은데, 삭제해야할까요?
    @GetMapping
    public ResponseEntity<List<ProductDto>> findAllProducts() {
        List<ProductDto> products = productService.findAllProducts();
        return ResponseEntity.ok(products);
    }

    //todo (질문3) : responseEntity의 status를 넘겨줄 때, uri 넘겨줘야하는 이유? 넘겨준 uri 정보로 리다이렉트 가능하다.
    @PostMapping
    public ResponseEntity<Void> saveProduct(@RequestBody ProductSaveRequestDto productSaveRequestDto) {
        long productId = productService.saveProduct(productSaveRequestDto);
        return ResponseEntity.created(URI.create("products"+ productId)).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateProduct(@RequestBody ProductUpdateRequestDto productUpdateRequestDto) {
        productService.updateProduct(productUpdateRequestDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}
