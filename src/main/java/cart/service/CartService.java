package cart.service;

import cart.dao.CartDao;
import cart.dao.ProductDao;
import cart.dao.UserDao;
import cart.dto.*;
import cart.entity.CartEntity;
import cart.entity.ProductEntity;
import cart.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    private static final int MINAFFECTEDROW = 1;

    private final ProductDao productDao;
    private final UserDao userDao;
    private final CartDao cartDao;

    public CartService(ProductDao productDao, UserDao userDao, CartDao cartDao) {
        this.productDao = productDao;
        this.userDao = userDao;
        this.cartDao = cartDao;
    }

    public List<UserResponseDto> getUsers() {
        final List<UserEntity> users = userDao.selectAll();

        return users.stream()
                .map(UserResponseDto::from)
                .collect(Collectors.toUnmodifiableList());
    }

    public List<ProductResponseDto> getCartProducts(final int userId) {
        List<CartEntity> carts = cartDao.selectByUserId(userId);

        List<ProductEntity> cartItems = carts.stream()
                .map(cartEntity -> productDao.selectById(cartEntity.getProductId()))
                .collect(Collectors.toUnmodifiableList());

        return cartItems.stream()
                .map(ProductResponseDto::from)
                .collect(Collectors.toUnmodifiableList());
    }

    public int addCartItem(final CartItem cartItem) {
        return cartDao.insert(new CartEntity(cartItem.getUserId(), cartItem.getProductId()));
    }

    public int deleteCartItem(final CartItem cartItem) {
        int affectedRow = cartDao.delete(new CartEntity(cartItem.getUserId(), cartItem.getProductId()));
        if (affectedRow < MINAFFECTEDROW) {
            throw new IllegalArgumentException("장바구니에 존재하지 않는 상품입니다.");
        }

        return affectedRow;
    }
}
