package woowacourse.shoppingcart.product.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.product.dao.ProductDao;
import woowacourse.shoppingcart.product.domain.Product;
import woowacourse.shoppingcart.product.dto.ProductResponse;
import woowacourse.shoppingcart.product.dto.ThumbnailImage;

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
}
