package cart.controller;

import cart.dao.ProductDao;
import cart.domain.Product;
import cart.dto.ProductRequest.AddDto;
import cart.dto.ProductRequest.UpdateDto;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
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
@Transactional
public class ProductController {

    private final ProductDao productDao;

    public ProductController(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void create(@Valid @RequestBody AddDto productDto) {
        productDao.insert(new Product(
                productDto.getName(),
                productDto.getImageUrl(),
                productDto.getPrice()
        ));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping
    public void update(@Valid @RequestBody UpdateDto productDto) {
        validateIdExist(productDto.getId());
        productDao.update(new Product(
                productDto.getId(),
                productDto.getName(),
                productDto.getImageUrl(),
                productDto.getPrice()
        ));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        validateIdExist(id);
        productDao.deleteById(id);
    }

    private void validateIdExist(Long id) {
        if (productDao.isExist(id)) {
            return;
        }
        throw new IllegalArgumentException("존재하지 않는 ID 입니다." + id);
    }
}
