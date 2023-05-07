package cart.business;

import cart.entity.Cart;
import cart.entity.Product;
import cart.persistence.CartDao;
import cart.presentation.dto.CartRequest;
import cart.presentation.dto.CartResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartService {

    private CartDao cartDao;

    public CartService(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    @Transactional
    public Integer create(CartRequest request) {
        Cart cart = makeCartFromRequest(request);

        return cartDao.insert(cart);
    }

    public List<Product> findProductsByMemberId(Integer memberId) {
        return cartDao.findAllProductsByMemberId(memberId);
    }

    @Transactional(readOnly = true)
    public List<Cart> read() {
        return cartDao.findAll();
    }

    @Transactional
    public Integer delete(Integer id) {
        return cartDao.remove(id);
    }

    private Cart makeCartFromRequest(CartRequest request) {
        return new Cart(null, request.getMemberId());
    }

    private Cart makeCartFromResponse(CartResponse response) {
        return new Cart(response.getId(), response.getMemberId());
    }
}
