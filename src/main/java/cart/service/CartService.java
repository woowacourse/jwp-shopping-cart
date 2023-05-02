package cart.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import cart.dao.cart.CartDao;
import cart.dao.entity.Product;
import cart.dao.entity.User;
import cart.dto.product.ProductMapper;
import cart.dto.product.ProductResponse;
import cart.dto.user.UserMapper;
import cart.dto.user.UserRequest;

@Service
public class CartService {

    private final UserMapper userMapper;
    private final ProductMapper productMapper;
    private final CartDao cartDao;

    public CartService(UserMapper userMapper, ProductMapper productMapper, CartDao cartDao) {
        this.userMapper = userMapper;
        this.productMapper = productMapper;
        this.cartDao = cartDao;
    }

    public List<ProductResponse> findAllProductsInCart(UserRequest userRequest) {
        final User user = userMapper.toEntity(userRequest);
        final List<Product> products = cartDao.findAllProductInCart(user);
        return products.stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toUnmodifiableList());
    }

    public void removeProductInCart(UserRequest userRequest, Long productId) {
        final User user = userMapper.toEntity(userRequest);
        cartDao.removeProductInCart(user, productId);
    }
}
