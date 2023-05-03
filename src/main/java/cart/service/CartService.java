package cart.service;

import cart.dao.CartDao;
import cart.dto.CartItemResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartDao cartDao;

    public CartService(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    public List<CartItemResponseDto> findAll(String memberEmail) {
        return cartDao.findAll(memberEmail).stream()
                .map(CartItemResponseDto::new)
                .collect(Collectors.toList());
    }

}
