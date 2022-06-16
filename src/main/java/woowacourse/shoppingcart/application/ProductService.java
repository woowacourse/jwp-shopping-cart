package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.domain.LoginCustomer;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.ProductResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {
    private final ProductDao productDao;
    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;

    public ProductService(final ProductDao productDao, final CartItemDao cartItemDao, final CustomerDao customerDao) {
        this.productDao = productDao;
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findProducts(final LoginCustomer loginCustomer) {
        List<Product> products = productDao.findProducts();

        if(loginCustomer.isUnauthorized()){
            return products.stream()
                    .map(ProductResponse::of)
                    .collect(Collectors.toList());
        }

        Long userId = customerDao.findIdByUserName(loginCustomer.getUserName());

        return products.stream()
                .map(Product::getId)
                .map(productId -> assembleProductResponse(userId, productId))
                .collect(Collectors.toList());
    }

    public Long addProduct(final Product product) {
        return productDao.save(product);
    }

    @Transactional(readOnly = true)
    public ProductResponse findProductById(final LoginCustomer loginCustomer, final Long productId) {
        Product product = productDao.findProductById(productId);
        if(loginCustomer.isUnauthorized()){
            return ProductResponse.of(product);
        }

        Long userId = customerDao.findIdByUserName(loginCustomer.getUserName());
        return assembleProductResponse(userId, productId);
    }

    private ProductResponse assembleProductResponse(Long userId, Long productId) {
        Product product = productDao.findProductById(productId);
        if(!cartItemDao.existByCustomerIdAndProductId(userId, productId)){
            return ProductResponse.of(product);
        }

        Long cartId = cartItemDao.findIdByCustomerIdAndProductId(userId, productId);
        int cartQuantity = cartItemDao.findQuantityById(userId);
        return ProductResponse.of(product, cartId, cartQuantity);
    }

    public void deleteProductById(final Long productId) {
        productDao.delete(productId);
    }
}
