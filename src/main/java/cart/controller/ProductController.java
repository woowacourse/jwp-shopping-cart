package cart.controller;

import cart.dao.ProductDao;
import cart.domain.Product;
import cart.dto.ErrorDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
    @PostMapping(path = "/add")
    public void create(@Valid @RequestBody ProductAddRequest productDto) {
        productDao.insert(new Product(
                productDto.getName(),
                productDto.getImageUrl(),
                productDto.getPrice()
        ));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(path = "/edit")
    public void update(@Valid @RequestBody ProductUpdateRequest productDto) {
        productDao.update(new Product(
                productDto.getId(),
                productDto.getName(),
                productDto.getImageUrl(),
                productDto.getPrice()
        ));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") @Positive Long id) {
        productDao.deleteById(id);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorDto handleException(MethodArgumentNotValidException exception) {
        FieldError foundError = (FieldError) exception.getBindingResult()
                .getAllErrors().stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("예외 메시지 정보가 존재하지 않습니다."));
        log.error("MethodArgumentNotValid message={}", foundError.getDefaultMessage());
        return new ErrorDto(foundError.getDefaultMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorDto handleException(ConstraintViolationException exception) {
        log.error("ConstraintViolationException message={}", exception.getMessage());
        return new ErrorDto(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorDto handleException(IllegalArgumentException exception) {
        log.error("IllegalArgumentException message={}", exception.getMessage());
        return new ErrorDto(exception.getMessage());
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
