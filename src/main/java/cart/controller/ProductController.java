package cart.controller;

import cart.dao.ProductDao;
import cart.domain.Product;
import cart.dto.ErrorDto;
import cart.dto.ProductDto;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductDao productDao;

    public ProductController(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @PostMapping(path = "/add")
    public ResponseEntity<Void> create(@Valid @RequestBody ProductDto productDto) {
        productDao.insert(new Product(productDto.getName(), productDto.getImage(), productDto.getPrice()));
        return ResponseEntity.ok().build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorDto handle(MethodArgumentNotValidException exception) {
        FieldError foundError = (FieldError) exception.getBindingResult()
                .getAllErrors().stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("예외 메시지 정보가 존재하지 않습니다."));
        return new ErrorDto(foundError.getField(), foundError.getDefaultMessage());
    }
}
