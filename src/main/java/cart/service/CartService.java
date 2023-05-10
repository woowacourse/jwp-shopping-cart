package cart.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import cart.dao.cart.CartDao;
import cart.dao.entity.CartProduct;
import cart.dao.entity.User;
import cart.dto.cart.CartProductMapper;
import cart.dto.cart.CartProductResponse;
import cart.dto.product.ProductMapper;
import cart.dto.user.UserMapper;
import cart.dto.user.UserRequest;

@Service
public class CartService {

    private final UserMapper userMapper;
    private final CartProductMapper cartProductMapper;
    private final CartDao cartDao;

    public CartService(UserMapper userMapper, ProductMapper productMapper, CartProductMapper cartProductMapper,
                       CartDao cartDao) {
        this.userMapper = userMapper;
        this.cartProductMapper = cartProductMapper;
        this.cartDao = cartDao;
    }

    public Long addProduct(UserRequest userRequest, Long productId) {
        final User user = userMapper.toEntity(userRequest);
        return cartDao.addProduct(user, productId);
    }

    public List<CartProductResponse> findAllProductsInCart(UserRequest userRequest) {
        final User user = userMapper.toEntity(userRequest);
        final List<CartProduct> products = cartDao.findAllProductInCart(user);
        return products.stream()
                .map(cartProductMapper::toResponse)
                .collect(Collectors.toUnmodifiableList());
    }

    public void removeProductInCart(UserRequest userRequest, Long cartId) {
        final User user = userMapper.toEntity(userRequest);
        cartDao.removeProductInCart(user, cartId);
    }
}
