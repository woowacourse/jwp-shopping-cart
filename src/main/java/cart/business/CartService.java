package cart.business;

import cart.entity.Cart;
import cart.entity.Member;
import cart.entity.Product;
import cart.persistence.CartDao;
import cart.presentation.dto.CartRequest;
import cart.presentation.dto.CartResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private CartDao cartDao;

    public CartService(CartDao cartDao) {
        this.cartDao = cartDao;
    }

//    @Transactional
//    public Integer create(CartRequest request) {
//        Cart cart = makeCartFromRequest(request);
//
//        return cartDao.insert(cart);
//    }

    public List<Product> findProductsByMemberId(Integer memberId) {
        return cartDao.findAllProductsByMemberId(memberId);
    }

    @Transactional
    public Integer delete(Integer id) {
        return cartDao.remove(id);
    }

    public Optional<Member> findMemberByEmail(String email) {
        return cartDao.findByEmail(email);
    }

    private Cart makeCartFromRequest(CartRequest request) {
        return new Cart(null, request.getMemberId());
    }

    private Cart makeCartFromResponse(CartResponse response) {
        return new Cart(response.getId(), response.getMemberId());
    }
}
