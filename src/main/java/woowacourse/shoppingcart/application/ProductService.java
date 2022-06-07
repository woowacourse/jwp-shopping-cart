package woowacourse.shoppingcart.application;

import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.support.User;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;

import java.util.List;
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

    public List<ProductResponse> findProducts(User user) {
        List<Product> products = productDao.findProducts();

        if (user.isNonMember()) {
            return createProductResponses(
                    products, user.getId(), (customerId, productId) -> false);
        }

        return createProductResponses(
                products, user.getId(), cartItemDao::existByCustomerIdAndProductId);
    }

    private List<ProductResponse> createProductResponses(List<Product> products,
                                                         Long customerId,
                                                         BiFunction<Long, Long, Boolean> checkIsStored) {
        return products.stream()
                .map(product -> ProductResponse.from(product, checkIsStored.apply(customerId, product.getId())))
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
