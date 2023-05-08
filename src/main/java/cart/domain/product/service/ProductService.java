package cart.domain.product.service;

import cart.dao.ProductDao;
import cart.domain.product.dto.ProductCreateDto;
import cart.domain.product.dto.ProductDto;
import cart.domain.product.dto.ProductUpdateDto;
import cart.domain.product.entity.Product;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional
    public ProductDto create(final ProductCreateDto productCreateDto) {
        final Product product = new Product(null, productCreateDto.getName(),
            productCreateDto.getPrice(), productCreateDto.getImageUrl(), null, null);
        final Product savedProduct = productDao.save(product);
        return ProductDto.of(savedProduct);
    }

    public List<ProductDto> findAll() {
        final List<Product> products = productDao.findAll();
        return products.stream().map(ProductDto::of).collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public void update(final ProductUpdateDto productUpdateDto) {
        final Product product = new Product(productUpdateDto.getId(), productUpdateDto.getName(),
            productUpdateDto.getPrice(), productUpdateDto.getImageUrl(), null, null);
        final int count = productDao.update(product);
        checkProductExist(count);
    }

    @Transactional
    public void delete(final Long id) {
        final int count = productDao.delete(id);
        checkProductExist(count);
    }

    private void checkProductExist(final int count) {
        if (count == 0) {
            throw new IllegalArgumentException("존재하지 않는 아이템입니다.");
        }
    }
}
