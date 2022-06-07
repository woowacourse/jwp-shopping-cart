package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.domain.cart.Quantity;
import woowacourse.shoppingcart.domain.customer.CustomerId;
import woowacourse.shoppingcart.domain.product.ProductId;
import woowacourse.shoppingcart.dto.CartItemRequest;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.RemovedCartItemsRequest;
import woowacourse.shoppingcart.exception.InvalidCartItemException;
import woowacourse.shoppingcart.exception.InvalidProductException;

import java.util.List;
import java.util.stream.Collectors;

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

    public CartItemResponse addCartItem(String token, CartItemRequest cartItemRequest) {
        customerService.validateToken(token);
        final CustomerId customerId = new CustomerId(customerService.getCustomerId(token));
        ProductId productId = new ProductId(cartItemRequest.getId());
        checkExistenceInAllProducts(productId);
        checkExistenceInCart(customerId, productId);
        cartItemDao.save(customerId, productId, new Quantity(cartItemRequest.getQuantity()));
        return new CartItemResponse(customerId.getValue(), cartItemRequest.getQuantity());
    }

    private void checkExistenceInCart(CustomerId customerId, ProductId productId) {
        if (cartItemDao.exists(customerId, productId)) {
            throw new InvalidCartItemException("이미 해당하는 상품이 장바구니에 있습니다.");
        }
    }

    private void checkExistenceInAllProducts(ProductId productId) {
        if (!productService.exists(productId)) {
            throw new InvalidProductException();
        }
    }

    public void removeCartItems(String token, RemovedCartItemsRequest removedCartItemsRequest) {
        final CustomerId customerId = new CustomerId(customerService.getCustomerId(token));
        List<ProductId> productIds = removedCartItemsRequest.getIds().stream()
                .map(ProductId::new)
                .collect(Collectors.toList());
        if (!productIds.stream()
                .allMatch(productId -> cartItemDao.exists(customerId, productId))) {
            throw new InvalidCartItemException();
        }
        cartItemDao.deleteCartItems(customerId, productIds);
    }
}
