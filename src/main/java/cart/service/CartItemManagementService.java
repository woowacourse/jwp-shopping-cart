package cart.service;

import cart.dao.CartItemDao;
import cart.dto.CartItemDto;
import org.springframework.stereotype.Service;

@Service
public class CartItemManagementService {

    private final CartItemDao cartItemDao;

    public CartItemManagementService(final CartItemDao cartItemDao) {
        this.cartItemDao = cartItemDao;
    }

    public long save(final CartItemDto cartItemDto) {
        return cartItemDao.insert(CartItemDto.toEntity(cartItemDto));
    }

}
