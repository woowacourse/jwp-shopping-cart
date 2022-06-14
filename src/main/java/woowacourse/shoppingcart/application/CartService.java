package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartDao;
import woowacourse.shoppingcart.domain.Carts;
import woowacourse.shoppingcart.domain.cart.Quantity;
import woowacourse.shoppingcart.domain.customer.CustomerId;
import woowacourse.shoppingcart.domain.product.ProductId;
import woowacourse.shoppingcart.dto.cart.*;
import woowacourse.shoppingcart.exception.InvalidCartException;
import woowacourse.shoppingcart.exception.InvalidProductException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartDao cartDao;
    private final CustomerService customerService;
    private final ProductService productService;

    public CartService(final CartDao cartDao, final CustomerService customerService, final ProductService productService) {
        this.cartDao = cartDao;
        this.customerService = customerService;
        this.productService = productService;
    }

    public CartQuantityResponse addCart(final Long id, final CartRequest cartRequest) {
        final CustomerId customerId = new CustomerId(id);
        final ProductId productId = new ProductId(cartRequest.getProductId());
        checkExistenceInAllProducts(productId);
        checkExistenceInCart(customerId, productId);
        cartDao.save(customerId, productId, new Quantity(cartRequest.getQuantity()));
        return new CartQuantityResponse(customerId.getValue(), cartRequest.getQuantity());
    }

    public CartsResponse findCarts(final Long customerId) {
        final Carts carts = new Carts(cartDao.getAllCartsBy(new CustomerId(customerId)));
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

    public void removeCarts(final Long id, final RemovedCartsRequest removedCartsRequest) {
        final CustomerId customerId = new CustomerId(id);
        final List<ProductId> productIds = removedCartsRequest.getProductIds().stream()
                .map(ProductId::new)
                .collect(Collectors.toList());
        if (!productIds.stream()
                .allMatch(productId -> cartDao.exists(customerId, productId))) {
            throw new InvalidCartException();
        }
        cartDao.deleteCarts(customerId, productIds);
    }

    public void editCart(final Long id, final CartRequest cartRequest) {
        final CustomerId customerId = new CustomerId(id);
        final ProductId productId = new ProductId(cartRequest.getProductId());
        checkExistenceInAllProducts(productId);
        checkNoneExistenceInCart(customerId, productId);
        cartDao.edit(customerId, productId, new Quantity(cartRequest.getQuantity()));
    }

    private void checkExistenceInAllProducts(final ProductId productId) {
        if (!productService.exists(productId)) {
            throw new InvalidProductException();
        }
    }

    private void checkExistenceInCart(final CustomerId customerId, final ProductId productId) {
        if (cartDao.exists(customerId, productId)) {
            throw new InvalidCartException("이미 해당하는 상품이 장바구니에 있습니다.");
        }
    }

    private void checkNoneExistenceInCart(final CustomerId customerId, final ProductId productId) {
        if (!cartDao.exists(customerId, productId)) {
            throw new InvalidCartException("해당하는 상품이 장바구니에 없습니다.");
        }
    }
}
