package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.application.exception.InvalidCartItemException;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.ProductRequest;
import woowacourse.shoppingcart.dto.ProductResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {
    private final ProductDao productDao;
    private final CartItemDao cartItemDao;

    public ProductService(final ProductDao productDao, final CartItemDao cartItemDao) {
        this.productDao = productDao;
        this.cartItemDao = cartItemDao;
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        List<Product> products = productDao.findProducts();
        return products.stream()
                .map(ProductResponse::new)
                .collect(Collectors.toList());
    }


    private void checkSameProduct(final Product product, final ProductResponse productResponse, final Long customerId) {
        List<Long> productIds = cartItemDao.findProductIdsByCustomerId(customerId);

        if (productIds.contains(product.getId())) {
            Cart cart = cartItemDao.findIdAndQuantityByProductId(product.getId(), customerId)
                    .orElseThrow(InvalidCartItemException::new);
            productResponse.addCartQuantity(cart);
        }
    }

    public Long addProduct(final ProductRequest.AllProperties productRequest) {
        return productDao.save(productRequest.toEntity());
    }

    @Transactional(readOnly = true)
    public ProductResponse findById(final Long productId) {
        return new ProductResponse(productDao.findProductById(productId));
    }

    public void deleteById(final Long productId) {
        productDao.delete(productId);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findProductsByCustomer(final Customer customer) {
        List<Product> products = productDao.findProducts();
        return getProductInCart(products, customer);
    }

    private List<ProductResponse> getProductInCart(final List<Product> products, final Customer customer) {
        List<ProductResponse> productResponses = new ArrayList<>();
        for (final Product product : products) {
            ProductResponse productResponse = new ProductResponse(product);
            checkSameProduct(product, productResponse, customer.getId());
            productResponses.add(productResponse);
        }
        return productResponses;
    }
}
