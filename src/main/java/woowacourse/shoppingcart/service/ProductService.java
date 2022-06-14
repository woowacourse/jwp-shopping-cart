package woowacourse.shoppingcart.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Image;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.ImageDto;
import woowacourse.shoppingcart.dto.ProductRequest;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public Long addProduct(final ProductRequest request) {
        final ImageDto thumbnailImage = request.getThumbnailImage();
        final Product product = new Product(request.getName(), request.getPrice(),
                request.getStockQuantity(), new Image(thumbnailImage.getUrl(), thumbnailImage.getAlt()));
        return productDao.save(product);
    }

    public List<Product> findProducts() {
        return productDao.findProducts();
    }

    public Product findById(final Long productId) {
        return productDao.findById(productId);
    }

    public void deleteById(final Long productId) {
        productDao.deleteById(productId);
    }

    public void updateQuantity(final Long productId, final int quantity) {
        productDao.updateQuantity(productId, quantity);
    }
}
