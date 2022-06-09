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

    public CartService(final CartItemDao cartItemDao, final CustomerService customerService, final ProductService productService) {
        this.cartItemDao = cartItemDao;
        this.customerService = customerService;
        this.productService = productService;
    }

    public CartItemResponse addCartItem(final Long id, final CartItemRequest cartItemRequest) {
        final CustomerId customerId = new CustomerId(id);
        final ProductId productId = new ProductId(cartItemRequest.getProductId());
        checkExistenceInAllProducts(productId);
        checkExistenceInCart(customerId, productId);
        cartItemDao.save(customerId, productId, new Quantity(cartItemRequest.getQuantity()));
        return new CartItemResponse(customerId.getValue(), cartItemRequest.getQuantity());
    }

    public CartsResponse findCartItems(final Long customerId) {
        final Carts carts = new Carts(cartItemDao.getAllCartsBy(new CustomerId(customerId)));
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

    public void removeCartItems(final Long id, final RemovedCartItemsRequest removedCartItemsRequest) {
        final CustomerId customerId = new CustomerId(id);
        final List<ProductId> productIds = removedCartItemsRequest.getProductIds().stream()
                .map(ProductId::new)
                .collect(Collectors.toList());
        if (!productIds.stream()
                .allMatch(productId -> cartItemDao.exists(customerId, productId))) {
            throw new InvalidCartItemException();
        }
        cartItemDao.deleteCartItems(customerId, productIds);
    }

    public void editCartItem(final Long id, final CartItemRequest cartItemRequest) {
        final CustomerId customerId = new CustomerId(id);
        final ProductId productId = new ProductId(cartItemRequest.getProductId());
        checkExistenceInAllProducts(productId);
        checkNoneExistenceInCart(customerId, productId);
        cartItemDao.edit(customerId, productId, new Quantity(cartItemRequest.getQuantity()));
    }

    private void checkExistenceInAllProducts(final ProductId productId) {
        if (!productService.exists(productId)) {
            throw new InvalidProductException();
        }
    }

    private void checkExistenceInCart(final CustomerId customerId, final ProductId productId) {
        if (cartItemDao.exists(customerId, productId)) {
            throw new InvalidCartItemException("이미 해당하는 상품이 장바구니에 있습니다.");
        }
    }

    private void checkNoneExistenceInCart(final CustomerId customerId, final ProductId productId) {
        if (!cartItemDao.exists(customerId, productId)) {
            throw new InvalidCartItemException("해당하는 상품이 장바구니에 없습니다.");
        }
    }
}
