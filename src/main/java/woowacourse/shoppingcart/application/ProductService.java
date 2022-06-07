package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.customer.CustomerId;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.domain.product.ProductId;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.dto.ProductsResponse;
import woowacourse.shoppingcart.exception.InvalidTokenException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {

    private final CustomerService customerService;
    private final ProductDao productDao;
    private final CartItemDao cartItemDao;

    public ProductService(final CustomerService customerService, final ProductDao productDao, final CartItemDao cartItemDao) {
        this.customerService = customerService;
        this.productDao = productDao;
        this.cartItemDao = cartItemDao;
    }

    public ProductsResponse findProducts(String token) {
        try {
            customerService.validateToken(token);
            CustomerId customerId = new CustomerId(customerService.getCustomerId(token));
            return new ProductsResponse(getProductsResponse(productDao.getProducts(), cartItemDao.getProductIdsBy(customerId)));
        } catch (InvalidTokenException e) {
            return new ProductsResponse(getProductsResponse(productDao.getProducts(), List.of()));
        }
    }

    private List<ProductResponse> getProductsResponse(List<Product> products, List<ProductId> allProductIdsInCarts) {
        return products.stream()
                .map(product ->
                        new ProductResponse(
                                product.getId().getValue(),
                                product.getName().getValue(),
                                product.getPrice().getValue(),
                                product.getThumbnail().getValue(),
                                product.in(allProductIdsInCarts)))
                .collect(Collectors.toList());
    }
}
