package cart.service;

import cart.dao.CartDao;
import cart.dto.entity.MemberCartEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartDao cartDao;

    public CartService(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    public List<MemberCartEntity> findAll() {
        return cartDao.findCartByMember(1L);
    }

}
