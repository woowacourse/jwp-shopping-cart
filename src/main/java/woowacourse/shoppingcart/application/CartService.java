package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.domain.cart.Quantity;
import woowacourse.shoppingcart.domain.customer.CustomerId;
import woowacourse.shoppingcart.domain.product.ProductId;
import woowacourse.shoppingcart.dto.CartItemRequest;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.exception.InvalidProductException;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartItemDao cartItemDao;
    private final CustomerService customerService;
    private final ProductService productService;

    public CartService(CartItemDao cartItemDao, CustomerService customerService, ProductService productService) {
        this.cartItemDao = cartItemDao;
        this.customerService = customerService;
        this.productService = productService;
    }

    public CartItemResponse addCart(String token, CartItemRequest cartItemRequest) {
        customerService.validateToken(token);
        final CustomerId customerId = new CustomerId(customerService.getCustomerId(token));
        checkExistenceInAllProducts(new ProductId(cartItemRequest.getId()));
        cartItemDao.save(customerId, new ProductId(cartItemRequest.getId()), new Quantity(cartItemRequest.getQuantity()));
        return new CartItemResponse(customerId.getValue(), cartItemRequest.getQuantity());
    }

    public List<ProductId> getProductIdsBy(CustomerId customerId) {
        return cartItemDao.getProductIdsBy(customerId);
    }

    private void checkExistenceInAllProducts(ProductId productId) {
        if (!productService.exists(productId)) {
            throw new InvalidProductException();
        }
    }
}
