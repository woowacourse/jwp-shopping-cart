package woowacourse.shoppingcart.product.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.product.application.dto.ProductDto;
import woowacourse.shoppingcart.product.dao.ProductDao;
import woowacourse.shoppingcart.product.domain.Product;
import woowacourse.shoppingcart.product.domain.ThumbnailImage;
import woowacourse.shoppingcart.product.ui.dto.ProductResponse;
import woowacourse.shoppingcart.product.ui.dto.ThumbnailImageDto;

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
    public ProductResponse addProduct(ProductDto productDto) {
        final ThumbnailImageDto thumbnailImageDto = productDto.getThumbnailImageDto();
        final Product product = productDao
                .save(new Product(productDto.getName(), productDto.getPrice(), productDto.getStockQuantity(),
                        new ThumbnailImage(thumbnailImageDto.getUrl(), thumbnailImageDto.getAlt())));
        return ProductResponse.from(product);
    }

    public ProductResponse findProductById(final Long productId) {
        final Product product = productDao.findProductById(productId);
        return ProductResponse.from(product);
    }
}
