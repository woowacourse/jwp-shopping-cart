package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.exception.BadRequestException;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.ProductRequest;
import woowacourse.shoppingcart.dto.ProductResponse;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<ProductResponse> findProducts() {
        List<Product> products = productDao.findProducts();
        return products.stream()
                .map(ProductService::convertResponseToProduct)
                .collect(Collectors.toList());
    }

    public Long addProduct(final ProductRequest request) {
        return productDao.save(ProductRequest.toProduct(request));
    }

    public ProductResponse findProductById(final Long productId) {
        Product product = productDao.findProductById(productId);
        return convertResponseToProduct(product);
    }

    public void deleteProductById(final Long productId) {
        final int affectedRows = productDao.delete(productId);
        if (affectedRows != 1) {
            throw new BadRequestException("상품이 삭제 되지 않았습니다.");
        }
    }

    public static ProductResponse convertResponseToProduct(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getImageUrl(),
                product.getDescription(), product.getStock());
    }
}
