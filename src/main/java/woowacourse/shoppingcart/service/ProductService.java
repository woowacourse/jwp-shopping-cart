package woowacourse.shoppingcart.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.ProductResponseDto;
import woowacourse.shoppingcart.exception.NotFoundProductException;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {
    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<ProductResponseDto> findProducts() {
        return productDao.findProducts()
                .stream()
                .map(product -> new ProductResponseDto(
                        product.getId(),
                        product.getThumbnailUrl(),
                        product.getName(),
                        product.getPrice(),
                        product.getQuantity()
                )).collect(Collectors.toList());
    }

    public Long addProduct(final Product product) {
        return productDao.save(product);
    }

    public ProductResponseDto findProductById(final Long productId) {
        final Product product = productDao.findProductById(productId)
                .orElseThrow(NotFoundProductException::new);
        return new ProductResponseDto(
                product.getId(),
                product.getThumbnailUrl(),
                product.getName(),
                product.getPrice(),
                product.getQuantity());
    }

    public void deleteProductById(final Long productId) {
        productDao.delete(productId);
    }
}
