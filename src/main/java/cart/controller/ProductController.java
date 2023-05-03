package cart.controller;

import cart.dto.ApiDataResponse;
import cart.dto.ApiResponse;
import cart.dto.ProductCreateRequestDto;
import cart.dto.ProductEditRequestDto;
import cart.dto.ProductsReadResponse;
import cart.service.ProductService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public ApiDataResponse<ProductsReadResponse> findProducts() {
        return ApiDataResponse.of(HttpStatus.OK, productService.findAll());
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ApiResponse createProduct(@RequestBody @Valid final ProductCreateRequestDto productCreateRequestDto) {
        productService.createProduct(productCreateRequestDto);

        return ApiResponse.from(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ApiResponse editProduct(@PathVariable final Long id, @RequestBody @Valid final ProductEditRequestDto productEditRequestDto) {
        productService.editProduct(id, productEditRequestDto);

        return ApiResponse.from(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ApiResponse deleteProduct(@PathVariable final Long id) {
        productService.deleteById(id);

        return ApiResponse.from(HttpStatus.OK);
    }
}
