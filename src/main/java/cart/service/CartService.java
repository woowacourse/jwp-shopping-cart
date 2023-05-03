package cart.service;

import cart.controller.dto.response.ProductResponse;
import cart.controller.dto.response.UserResponse;
import cart.dao.CartDao;
import cart.dao.ProductDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
public class CartService {

    private final ProductDao productDao;
    private final CartDao cartDao;

    public CartService(ProductDao productDao, CartDao cartDao) {
        this.productDao = productDao;
        this.cartDao = cartDao;
    }

    @Transactional
    public void addCart(UserResponse userResponse, Long productId) {
        if (!productDao.existById(productId)) {
            throw new IllegalStateException();
        }

        cartDao.create(userResponse.getId(), productId);
    }

    public List<ProductResponse> findCartByUser(UserResponse userResponse) {
        return cartDao.findByUserId(userResponse.getId())
                .stream()
                .map(ProductResponse::new)
                .collect(Collectors.toList());
    }
}
