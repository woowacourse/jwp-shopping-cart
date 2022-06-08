package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.application.dto.ProductDto;
import woowacourse.shoppingcart.application.dto.ProductsDto;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.notfound.ProductNotFoundException;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {

    private static final int NOTHING_DELETED = 0;

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public ProductsDto findProducts(int size, int page) {
        final int totalSize = productDao.findProducts().size();
        final List<Product> productsByPage = productDao.findProductsByPage(size, page);
        final List<ProductDto> products = productsByPage.stream()
                .map(ProductDto::from)
                .collect(Collectors.toList());
        return new ProductsDto(totalSize, products);
    }

    public Long addProduct(final Product product) {
        return productDao.save(product);
    }

    public Product findProductById(final Long productId) {
        try {
            return productDao.findProductById(productId);
        } catch (EmptyResultDataAccessException e) {
            throw new ProductNotFoundException();
        }
    }

    public void deleteProductById(final Long productId) {
        final int deletedCount = productDao.delete(productId);
        if (deletedCount == NOTHING_DELETED) {
            throw new ProductNotFoundException();
        }
    }
}
