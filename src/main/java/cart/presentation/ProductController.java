package cart.presentation;


import cart.business.ProductCRUDService;
import cart.presentation.dto.ProductDto;
import cart.presentation.dto.ProductIdDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/product")
public class ProductController {

    private final ProductCRUDService productCRUDService;

    public ProductController(ProductCRUDService productCRUDService) {
        this.productCRUDService = productCRUDService;
    }

    @PostMapping
    public void productCreate(@RequestBody @Valid ProductDto request) {
        productCRUDService.create(ProductConverter.toProductWithoutId(request));
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> productRead() {
        List<ProductDto> response = productCRUDService.readAll()
                .stream()
                .map(ProductConverter::toProductDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PutMapping
    public void productUpdate(@RequestBody @Valid ProductDto request) {
        productCRUDService.update(ProductConverter.toProductWithId(request));
    }

    @DeleteMapping
    public void productDelete(@RequestBody @Valid ProductIdDto request) {
        productCRUDService.delete(request.getId());
    }
}
