package cart.service;

import cart.dao.CartDao;
import cart.service.dto.CartDto;
import cart.service.dto.CartInfoDto;
import cart.service.exception.DuplicateCartException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartDao cartDao;

    public CartService(final CartDao cartDao) {
        this.cartDao = cartDao;
    }

    public long save(final CartDto cartDto, final long customerId) {
        boolean isProductAlreadyInCart = cartDao.isProductIdInCustomerCart(customerId, cartDto.getProductId());
        if (isProductAlreadyInCart) {
            throw new DuplicateCartException();
        }
        return cartDao.insert(customerId, cartDto.getProductId());
    }

    public List<CartInfoDto> findAllByCustomerId(final long customerId) {
        return cartDao.findAllCartProductByCustomerId(customerId)
                .stream()
                .map(CartInfoDto::from)
                .collect(Collectors.toList());
    }

    public void deleteById(final long cartId) {
        cartDao.deleteById(cartId);
    }

}
