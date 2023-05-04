package cart.service;

import cart.dto.CartProductResponse;
import cart.dto.ProductRequest;
import cart.dto.ProductResponse;
import cart.dto.UserResponse;
import cart.persistence.dao.JdbcCartDao;
import cart.persistence.dao.JdbcProductDao;
import cart.persistence.dao.JdbcUserDao;
import cart.persistence.entity.CartEntity;
import cart.persistence.entity.ProductEntity;
import cart.persistence.entity.UserEntity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    public static final int EXPECTED_SIZE = 1;
    private final JdbcProductDao productDao;
    private final JdbcUserDao userDao;
    private final JdbcCartDao cartDao;

    public CartService(JdbcProductDao productDao, JdbcUserDao userDao, JdbcCartDao cartDao) {
        this.productDao = productDao;
        this.userDao = userDao;
        this.cartDao = cartDao;
    }

    public long createProduct(final ProductRequest productRequest) {
        final ProductEntity productEntity =
                new ProductEntity(productRequest.getName(), productRequest.getPrice(), productRequest.getImageUrl());
        return productDao.save(productEntity);
    }

    public List<ProductResponse> readAllProducts() {
        final List<ProductEntity> products = productDao.findAll();
        return products.stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
    }

    public void updateProduct(final long id, final ProductRequest productRequest) {
        final ProductEntity productEntity =
                new ProductEntity(id, productRequest.getName(), productRequest.getPrice(), productRequest.getImageUrl());
        if (productDao.update(productEntity) != EXPECTED_SIZE) {
            throw new EmptyResultDataAccessException(EXPECTED_SIZE);
        }
    }

    public void deleteProduct(final long id) {
        if (productDao.deleteById(id) != EXPECTED_SIZE) {
            throw new EmptyResultDataAccessException(EXPECTED_SIZE);
        }
    }

    public List<UserResponse> readAllUsers() {
        final List<UserEntity> products = userDao.findAll();
        return products.stream()
                .map(UserResponse::from)
                .collect(Collectors.toList());
    }

    public List<CartProductResponse> readCart(final String email) {
        final List<ProductEntity> products = productDao.findProductsByUser(email);

        return products.stream()
                .map(CartProductResponse::from)
                .collect(Collectors.toList());
    }

    public long addCartItem(final String email, final Long id) {
        final Long userId = userDao.findUserIdByEmail(email);
        final CartEntity cartEntity = new CartEntity(userId, id);

        return cartDao.save(cartEntity);
    }

    public void deleteCartItem(final String email, final Long id) {
        final Long userId = userDao.findUserIdByEmail(email);
        cartDao.deleteById(id);
    }
}
