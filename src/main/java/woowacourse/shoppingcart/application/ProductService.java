package woowacourse.shoppingcart.application;

import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;

import java.util.List;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.dto.ThumbnailImage;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<ProductResponse> findProducts() {
        final List<Product> products = productDao.findProducts();
        return products.stream()
                .map(ProductResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public ProductResponse addProduct(String name, Integer price, Long stockQuantity, String imageUrl, String imageAlt) {
        final Product product = productDao
                .save(new Product(name, price, stockQuantity, new ThumbnailImage(imageUrl, imageAlt)));
        return ProductResponse.from(product);
    }

    public ProductResponse findProductById(final Long productId) {
        final Product product = productDao.findProductById(productId);
        return ProductResponse.from(product);
    }

    @Transactional
    public void deleteProductById(final Long productId) {
        productDao.delete(productId);
    }
}
