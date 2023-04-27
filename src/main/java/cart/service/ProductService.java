package cart.service;

import static java.util.stream.Collectors.toUnmodifiableList;

import cart.dao.ProductDao;
import cart.domain.Product;
import cart.dto.ProductDto;
import cart.dto.ProductSaveRequestDto;
import cart.dto.ProductUpdateRequestDto;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public Long save(final ProductSaveRequestDto request) {
        final Product product = new Product(request.getName(), request.getImage(), request.getPrice());

        return productDao.saveAndGetId(product)
                .orElseThrow(() -> new IllegalStateException("상품을 저장할 수 없습니다."));
    }

    public List<ProductDto> findAll() {
        return productDao.findAll().stream()
                .map(ProductDto::from)
                .collect(toUnmodifiableList());
    }

    public ProductDto findById(final Long id) {
        final Product product = productDao.findById(id)
                .orElseThrow(() -> new NoSuchElementException("상품을 찾을 수 없습니다."));
        return ProductDto.from(product);
    }

    public void update(final Long id, final ProductUpdateRequestDto request) {
        productDao.findById(id)
                .orElseThrow(() -> new NoSuchElementException("상품을 찾을 수 없습니다."));
        final Product savedProduct = new Product(id, request.getName(), request.getImage(), request.getPrice());
        productDao.update(savedProduct);
    }

    public void delete(final Long id) {
        productDao.findById(id)
                .orElseThrow(() -> new NoSuchElementException("상품을 찾을 수 없습니다."));
        productDao.delete(id);
    }
}
