package woowacourse.shoppingcart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.CartItemRepository;
import woowacourse.shoppingcart.domain.CartItems;
import woowacourse.shoppingcart.domain.Quantity;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.dto.CartItemRequest;
import woowacourse.shoppingcart.dto.CartItemResponse;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartItemService {

    private final CustomerDao customerDao;
    private final ProductDao productDao;
    private final CartItemRepository cartItemRepository;

    public CartItemService(CustomerDao customerDao, ProductDao productDao,
        CartItemRepository cartItemRepository) {
        this.customerDao = customerDao;
        this.productDao = productDao;
        this.cartItemRepository = cartItemRepository;
    }

    public CartItemResponse addCart(final String email, final CartItemRequest cartItemRequest) {
        Customer customer = customerDao.findByEmail(email);
        Product productById = productDao.findProductById(cartItemRequest.getProductId());
        CartItems cartItems = cartItemRepository.findByCustomerId(customer.getId());
        CartItem newCartItem = new CartItem(productById, new Quantity(cartItemRequest.getQuantity()));
        cartItems.add(newCartItem);
        long addedCartItemID = cartItemRepository.addCartItem(customer.getId(), newCartItem);
        return CartItemResponse.from(cartItemRepository.findById(addedCartItemID));
    }
}
