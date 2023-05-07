package cart.service;

import cart.auth.Credential;
import cart.controller.dto.AddCartItemRequest;
import cart.controller.dto.CartResponse;
import cart.controller.dto.DeleteCartItemRequest;
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

    public Long addItem(Credential credential, AddCartItemRequest addCartItemRequest) {
        return cartDao.save(new CartEntity(credential.getMemberId(), addCartItemRequest.getProductId()));
    }

    public List<CartResponse> findCartItems(Long id) {
        return cartDao.findProductsByMemberId(id);
    }

    public void deleteItem(final DeleteCartItemRequest deleteCartItemRequest) {
        cartDao.deleteById(deleteCartItemRequest.getProductId());
    }
}
