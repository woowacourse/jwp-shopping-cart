package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.domain.Carts;
import woowacourse.shoppingcart.domain.cart.Quantity;
import woowacourse.shoppingcart.domain.customer.CustomerId;
import woowacourse.shoppingcart.domain.product.ProductId;
import woowacourse.shoppingcart.dto.cart.*;
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
        final CustomerId customerId = new CustomerId(customerService.getCustomerId(token));
        ProductId productId = new ProductId(cartItemRequest.getProductId());
        checkExistenceInAllProducts(productId);
        checkExistenceInCart(customerId, productId);
        cartItemDao.save(customerId, productId, new Quantity(cartItemRequest.getQuantity()));
        return new CartItemResponse(customerId.getValue(), cartItemRequest.getQuantity());
    }

    public CartsResponse findCartItems(String token) {
        final CustomerId customerId = new CustomerId(customerService.getCustomerId(token));
        Carts carts = new Carts(cartItemDao.getAllCartsBy(customerId));
        return new CartsResponse(
                carts.getCarts().stream()
                .map(cart -> new CartResponse(
                        cart.getProduct().getId().getValue(),
                        cart.getProduct().getName().getValue(),
                        cart.getProduct().getPrice().getValue(),
                        cart.getProduct().getThumbnail().getValue(),
                        cart.getQuantity().getValue()))
                .collect(Collectors.toList()));
    }

    public void removeCartItems(String token, RemovedCartItemsRequest removedCartItemsRequest) {
        final CustomerId customerId = new CustomerId(customerService.getCustomerId(token));
        List<ProductId> productIds = removedCartItemsRequest.getProductIds().stream()
                .map(ProductId::new)
                .collect(Collectors.toList());
        if (!productIds.stream()
                .allMatch(productId -> cartItemDao.exists(customerId, productId))) {
            throw new InvalidCartItemException();
        }
        cartItemDao.deleteCartItems(customerId, productIds);
    }

    public void editCartItem(String token, CartItemRequest cartItemRequest) {
        final CustomerId customerId = new CustomerId(customerService.getCustomerId(token));
        final ProductId productId = new ProductId(cartItemRequest.getProductId());
        checkExistenceInAllProducts(productId);
        checkNoneExistenceInCart(customerId, productId);
        cartItemDao.edit(customerId, productId, new Quantity(cartItemRequest.getQuantity()));
    }

    private void checkExistenceInAllProducts(ProductId productId) {
        if (!productService.exists(productId)) {
            throw new InvalidProductException();
        }
    }

    private void checkExistenceInCart(CustomerId customerId, ProductId productId) {
        if (cartItemDao.exists(customerId, productId)) {
            throw new InvalidCartItemException("이미 해당하는 상품이 장바구니에 있습니다.");
        }
    }

    private void checkNoneExistenceInCart(CustomerId customerId, ProductId productId) {
        if (!cartItemDao.exists(customerId, productId)) {
            throw new InvalidCartItemException("해당하는 상품이 장바구니에 없습니다.");
        }
    }
}
