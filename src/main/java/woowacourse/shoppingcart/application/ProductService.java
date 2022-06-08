package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.function.ToIntBiFunction;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.support.User;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.dto.ProductsResponse;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {
    private final ProductDao productDao;
    private final CartItemDao cartItemDao;

    public ProductService(ProductDao productDao, CartItemDao cartItemDao) {
        this.productDao = productDao;
        this.cartItemDao = cartItemDao;
    }

    public ProductsResponse findProducts(User user) {
        List<Product> products = productDao.findProducts();

        if (user.isNonMember()) {
            return new ProductsResponse(
                    createProductResponses(products, user.getId(), (customerId, productId) -> 0));
        }

        return new ProductsResponse(
                createProductResponses(products, user.getId(), cartItemDao::findQuantityByCustomerIdAndProductId));
    }

    private List<ProductResponse> createProductResponses(List<Product> products,
                                                         Long customerId,
                                                         ToIntBiFunction<Long, Long> quantityProvider) {
        return products.stream()
                .map(product -> ProductResponse.from(product, quantityProvider.applyAsInt(customerId, product.getId())))
                .collect(Collectors.toList());
    }

    public Long addProduct(final Product product) {
        return productDao.save(product);
    }

    public Product findProductById(final Long productId) {
        return productDao.findProductById(productId);
    }

    public void deleteProductById(final Long productId) {
        productDao.delete(productId);
    }
}
