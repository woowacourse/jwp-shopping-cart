package cart.controller;

import cart.dto.ProductDto;
import cart.dto.ProductRequestDto;
import cart.service.ProductService;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
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
public class ProductApiController {

    private final ProductService productService;

    public ProductApiController(ProductService productService) {
        this.productService = productService;
    }

    //todo (질문1): (PageController)에서 admin 페이지를 보여주는 거랑 중복 코드인 것같은데, 삭제해야할까요?
    @GetMapping
    public ResponseEntity<List<ProductDto>> findAllProducts() {
        List<ProductDto> allProducts = productService.findAllProducts();
        return ResponseEntity.ok(allProducts);
    }

    @PostMapping
    public ResponseEntity<ProductDto> saveProduct(@RequestBody @Valid ProductRequestDto productRequestDto) {
        ProductDto savedProduct = productService.saveProduct(productRequestDto);
        return ResponseEntity.created(URI.create("products"+ savedProduct.getProductId())).body(savedProduct);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable long productId, @RequestBody @Valid ProductRequestDto productRequestDto) {
        ProductDto updatedProduct = productService.updateProduct(productId, productRequestDto);
        return ResponseEntity.ok(updatedProduct);
    }


    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}
