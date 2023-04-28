package cart.controller;

import cart.dao.ProductDao;
import cart.domain.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductDao productDao;

    public ProductController(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void create(@Valid @RequestBody ProductAddRequest productDto) {
        productDao.insert(new Product(
                productDto.getName(),
                productDto.getImageUrl(),
                productDto.getPrice()
        ));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping
    public void update(@Valid @RequestBody ProductUpdateRequest productDto) {
        productDao.update(new Product(
                productDto.getId(),
                productDto.getName(),
                productDto.getImageUrl(),
                productDto.getPrice()
        ));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") @Positive Long id) {
        productDao.deleteById(id);
    }

    @Getter
    @NoArgsConstructor
    private static class ProductAddRequest {

        @NotNull
        private String name;
        @JsonProperty("image-url")
        @NotNull
        private String imageUrl;
        @NotNull
        private Integer price;
    }

    @Getter
    @NoArgsConstructor
    private static class ProductUpdateRequest {

        @NotNull
        private Long id;
        @NotNull
        private String name;
        @JsonProperty("image-url")
        @NotNull
        private String imageUrl;
        @NotNull
        private Integer price;
    }
}
