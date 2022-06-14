package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Carts;
import woowacourse.shoppingcart.domain.Products;
import woowacourse.shoppingcart.domain.cart.Quantity;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.CustomerId;
import woowacourse.shoppingcart.domain.product.ProductId;
import woowacourse.shoppingcart.dto.product.ProductResponse;
import woowacourse.shoppingcart.dto.product.ProductsResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {

    private final ProductDao productDao;
    private final CartItemDao cartItemDao;

    public ProductService(final ProductDao productDao, final CustomerService customerService, final CartItemDao cartItemDao) {
        this.productDao = productDao;
        this.cartItemDao = cartItemDao;
    }

    public ProductsResponse findProducts(final Long id) {
        if (id.equals(Customer.GUEST)) {
            return new ProductsResponse(getProductsWithQuantityForMember(new Products(productDao.getProducts()), new Carts(cartItemDao.getAllCartsBy(new CustomerId(id)))));
        }
        return new ProductsResponse(getProductsWithQuantityForGuest(new Products(productDao.getProducts())));
    }

    private List<ProductResponse> getProductsWithQuantityForGuest(final Products products) {
        return products.getProducts().stream()
                .map(product ->
                        new ProductResponse(
                                product.getId().getValue(),
                                product.getName().getValue(),
                                product.getPrice().getValue(),
                                product.getThumbnail().getValue(),
                                Quantity.GUEST_QUANTITY
                        ))
                .collect(Collectors.toList());
    }

    private List<ProductResponse> getProductsWithQuantityForMember(final Products products, final Carts carts) {
        return products.getProducts().stream()
                .map(product ->
                        new ProductResponse(
                                product.getId().getValue(),
                                product.getName().getValue(),
                                product.getPrice().getValue(),
                                product.getThumbnail().getValue(),
                                carts.findQuantity(product).getValue()
                        ))
                .collect(Collectors.toList());
    }

    public boolean exists(final ProductId id) {
        return productDao.exists(id);
    }
}
