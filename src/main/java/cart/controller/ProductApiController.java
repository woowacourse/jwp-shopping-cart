package cart.controller;

import cart.dto.InsertProductRequestDto;
import cart.dto.ProductResponseDto;
import cart.dto.UpdateProductRequestDto;
import cart.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductApiController {

    private final ProductService productService;

    public ProductApiController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product")
    public List<ProductResponseDto> getProducts() {
        return productService.getProducts();
    }

    @PostMapping("/product")
    public void insertProduct(@RequestBody InsertProductRequestDto insertProductRequestDto) {
        validatePrice(insertProductRequestDto.getPrice());
        productService.addProduct(insertProductRequestDto);
    }

    @PatchMapping("/product")
    public void updateProduct(@RequestBody UpdateProductRequestDto updateProductRequestDto) {
        validatePrice(updateProductRequestDto.getPrice());
        productService.updateProduct(updateProductRequestDto);
    }

    @DeleteMapping("/product/{id}")
    public void deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
    }

    private void validatePrice(int price) {
        if (price < 0) {
            throw new IllegalArgumentException("가격은 음수일 수 없습니다.");
        }
    }
}
