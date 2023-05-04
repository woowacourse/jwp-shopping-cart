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
    private final int MINAFFECTEDROW = 1;

    private final ProductDao productDao;
    private final UserDao userDao;
    private final CartDao cartDao;

    public CartService(ProductDao productDao, UserDao userDao, CartDao cartDao) {
        this.productDao = productDao;
        this.userDao = userDao;
        this.cartDao = cartDao;
    }

    public void addProduct(final InsertRequestDto insertRequestDto) {
        productDao.insert(insertRequestDto.toEntity());
    }

    public List<ProductResponseDto> getProducts() {
        final List<ProductEntity> products = productDao.selectAll();

        return products.stream()
                .map(product -> new ProductResponseDto(
                        product.getId(),
                        product.getImage(),
                        product.getName(),
                        product.getPrice()
                ))
                .collect(Collectors.toUnmodifiableList());
    }

    public void updateProduct(final UpdateRequestDto updateRequestDto) {
        int affectedRow = productDao.update(updateRequestDto.toEntity());
        if (affectedRow < MINAFFECTEDROW) {
            throw new IllegalArgumentException("존재하지 않는 상품입니다.");
        }
    }

    public void deleteProduct(final int productId) {
        int affectedRow = productDao.delete(productId);
        if (affectedRow < MINAFFECTEDROW) {
            throw new IllegalArgumentException("존재하지 않는 상품입니다.");
        }
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

    public List<ProductResponseDto> getCartItems(final AuthInfo authInfo) {
        int userId = userDao.selectByAuth(authInfo);
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
}
