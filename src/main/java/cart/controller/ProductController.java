package cart.controller;

import cart.dao.ProductDao;
import cart.domain.Product;
import cart.dto.ErrorDto;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping(path = "/add")
    public ResponseEntity<Void> create(@Valid @RequestBody ProductAddRequest productDto) {
        productDao.insert(new Product(productDto.getName(), productDto.getImage(), productDto.getPrice()));
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/edit")
    public ResponseEntity<Void> update(@Valid @RequestBody ProductUpdateRequest productDto) {
        productDao.update(new Product(
                productDto.getId(),
                productDto.getName(),
                productDto.getImage(),
                productDto.getPrice())
        );
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") @Positive Long id) {
        productDao.deleteById(id);
        return ResponseEntity.ok().build();
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

    @Getter
    @NoArgsConstructor
    private static class ProductAddRequest {

        @NotNull
        @NotBlank(message = "상품 이름은 빈 문자열일 수 없습니다")
        @Length(min = 1, max = 30, message = "상품 이름은 최소 1, 최대 30글자입니다.")
        private String name;
        @NotNull
        @NotBlank(message = "이미지 URL은 빈 문자열일 수 없습니다")
        @Length(max = 1000, message = "이미지 URL 길이가 너무 깁니다.")
        private String image;
        @PositiveOrZero
        @Max(value = 1_000_000_000, message = "상품 금액이 너무 큽니다.")
        private int price;
    }

    @Getter
    @NoArgsConstructor
    private static class ProductUpdateRequest {

        @NotNull
        private long id;
        @NotNull
        @NotBlank(message = "상품 이름은 빈 문자열일 수 없습니다")
        @Length(min = 1, max = 30, message = "상품 이름은 최소 1, 최대 30글자입니다.")
        private String name;
        @NotNull
        @NotBlank(message = "이미지 URL은 빈 문자열일 수 없습니다")
        @Length(max = 1000, message = "이미지 URL 길이가 너무 깁니다.")
        private String image;
        @PositiveOrZero
        @Max(value = 1_000_000_000, message = "상품 금액이 너무 큽니다.")
        private int price;
    }
}
