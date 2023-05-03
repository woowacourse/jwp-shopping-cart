package cart.service;

import cart.dao.CartDao;
import cart.service.dto.CartRequest;
import cart.service.dto.CartResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartDao cartDao;

    public CartService(final CartDao cartDao) {
        this.cartDao = cartDao;
    }

    public long save(final CartRequest cartRequest, final long customerId) {
        boolean isProductAlreadyInCart = cartDao.isProductIdInCustomerCart(customerId, cartRequest.getProductId());
        if (isProductAlreadyInCart) {
            throw new DuplicateCartException();
        }
        return cartDao.insert(customerId, cartRequest.getProductId());
    }

    public List<CartResponse> findAllByCustomerId(final long customerId) {
        return cartDao.findAllCartProductByCustomerId(customerId)
                .stream()
                .map(CartResponse::from)
                .collect(Collectors.toList());
    }

    public void deleteById(final long cartId) {
        cartDao.deleteById(cartId);
    }

}
