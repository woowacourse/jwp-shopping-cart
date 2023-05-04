package cart.service;

import cart.dto.request.CartRequestDto;
import cart.entity.CartEntity;
import cart.repository.CartDao;
import cart.dto.response.CartItemResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartDao cartDao;

    public CartService(final CartDao cartDao) {
        this.cartDao = cartDao;
    }

    public int create(final CartRequestDto cartRequestDto, final int userId) {
        final CartEntity cart = new CartEntity(userId, cartRequestDto.getProductId());
        return cartDao.create(cart);
    }

    public List<CartItemResponseDto> getProductsInCart(final int userId) {
        return cartDao.findByUserId(userId);
    }

    public void delete(final int id, final int userId) {
        cartDao.deleteByIdAndUserId(id, userId);
    }
}
