package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.dao.ThumbnailImageDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.ProductRequest;
import woowacourse.shoppingcart.dto.ProductResponse;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {
    private final ProductDao productDao;
    private final ThumbnailImageDao thumbnailImageDao;

    public ProductService(ProductDao productDao, ThumbnailImageDao thumbnailImageDao) {
        this.productDao = productDao;
        this.thumbnailImageDao = thumbnailImageDao;
    }

    public Product addProduct(final ProductRequest productRequest) {
        Product product = new Product(productRequest.getName(), productRequest.getPrice(),
                productRequest.getStockQuantity(), productRequest.getThumbnailImage());

        Long productId = productDao.save(product);
        thumbnailImageDao.save(productRequest.getThumbnailImage(), productId);

        return new Product(productId, productRequest.getName(), productRequest.getPrice(),
                product.getStockQuantity(), product.getThumbnailImage());
    }

    public List<ProductResponse> findProducts() {
        List<Product> products = productDao.getAll();

        return products.stream()
                .map(ProductResponse::new)
                .collect(Collectors.toUnmodifiableList());
    }

    public ProductResponse findProductById(final Long productId) {
        Product product = productDao.getById(productId);
        return new ProductResponse(product);
    }

    public void deleteProductById(final Long productId) {
        productDao.delete(productId);
    }
}
