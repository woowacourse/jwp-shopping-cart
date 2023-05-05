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
                .map(user -> new UserResponseDto(
                        user.getEmail(),
                        user.getPassword()
                ))
                .collect(Collectors.toUnmodifiableList());
    }

    public List<ProductResponseDto> getCartItems(final int userId) {
        List<CartEntity> carts = cartDao.selectByUserId(userId);

        List<ProductEntity> cartItems = carts.stream()
                .map(cartEntity -> productDao.selectById(cartEntity.getProductId()))
                .collect(Collectors.toList());

        return cartItems.stream()
                .map(product -> new ProductResponseDto(
                        product.getId(),
                        product.getImage(),
                        product.getName(),
                        product.getPrice()
                ))
                .collect(Collectors.toUnmodifiableList());
    }

    public int addCartItem(final int userId, final int productId) {
        return cartDao.insert(new CartEntity(userId, productId));
    }

    public int deleteCartItem(final int userId, final int productId) {
        return cartDao.delete(userId, productId);
    }
}
