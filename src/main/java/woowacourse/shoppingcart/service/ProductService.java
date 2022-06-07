package woowacourse.shoppingcart.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Image;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.ImageDto;
import woowacourse.shoppingcart.dto.addProductRequest;
import woowacourse.shoppingcart.dto.ProductResponse;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<ProductResponse> findProducts() {
        final List<Product> products = productDao.findProducts();
        return products.stream()
                .map(product -> ProductResponse.of(product.getId(), product))
                .collect(Collectors.toUnmodifiableList());
    }

    public ProductResponse addProduct(final addProductRequest request) {
        final ImageDto thumbnailImage = request.getThumbnailImage();
        final Product product = new Product(request.getName(), request.getPrice(),
                request.getStockQuantity(), new Image(thumbnailImage.getUrl(), thumbnailImage.getAlt()));
        final Long productId = productDao.save(product);

        return ProductResponse.of(productId, product);
    }

    public ProductResponse findProductById(final Long productId) {
        final Product product = productDao.findProductById(productId);
        return ProductResponse.of(product.getId(), product);
    }

    public void deleteProductById(final Long productId) {
        productDao.deleteById(productId);
    }
}
