package cart.service;

import cart.dto.CartRequestDto;
import cart.dto.ProductResponseDto;
import cart.entity.CartEntity;
import cart.entity.ProductEntity;
import cart.repository.CartDao;
import cart.service.converter.ProductConverter;
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

    public List<ProductResponseDto> getProductsInCart(final int userId) {
        final List<ProductEntity> productEntities = cartDao.findProductByUserId(userId);
        return ProductConverter.entitiesToResponses(productEntities);
    }
}
