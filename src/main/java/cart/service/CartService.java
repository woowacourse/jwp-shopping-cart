package cart.service;

import cart.dao.CartDao;
import cart.dao.entity.Cart;
import cart.dto.CartResponse;
import cart.dto.CartResponses;
import cart.dto.CartSaveRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartDao cartDao;
    private final CartMapper cartMapper;

    public CartService(final CartDao cartDao, final CartMapper cartMapper) {
        this.cartDao = cartDao;
        this.cartMapper = cartMapper;
    }

    public Long save(final CartSaveRequest saveRequest) {
        return cartDao.save(cartMapper.mapFrom(saveRequest));
    }

    public CartResponses findAllByUserId(final Long userId) {
        final List<Cart> carts = cartDao.findAllByUserId(userId);

        final List<CartResponse> cartResponses = carts.stream()
                .map(CartResponse::new)
                .collect(Collectors.toList());
        return new CartResponses(cartResponses);
    }
}
