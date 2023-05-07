package cart.service;

import cart.auth.Credential;
import cart.controller.dto.AddCartItemRequest;
import cart.controller.dto.CartResponse;
import cart.dao.CartDao;
import cart.entity.CartEntity;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartDao cartDao;

    public CartService(final CartDao cartDao) {
        this.cartDao = cartDao;
    }

    public Long addCartProducts(Credential credential, AddCartItemRequest addCartItemRequest) {
        return cartDao.save(new CartEntity(credential.getMemberId(), addCartItemRequest.getProductId()));
    }

    public List<CartResponse> findCartProducts(Long id) {
        return cartDao.findProductsByMemberId(id);
    }

    public void deleteProducts(final Long cartId) {
        cartDao.deleteById(cartId);
    }
}
