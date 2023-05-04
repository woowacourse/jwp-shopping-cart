package cart.service;

import cart.dao.CartDao;
import cart.dao.Dao;
import cart.dto.CartDto;
import cart.entity.Cart;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {
    private final Dao dao;

    public CartService(CartDao dao) {
        this.dao = dao;
    }

    public List<CartDto> getCartsByEmail(String email) {
        List<Cart> carts = dao.selectById(email);
        return carts.stream()
                .map(CartDto::from)
                .collect(Collectors.toUnmodifiableList());
    }
}
