package cart.service;

import cart.dto.CartItemDto;
import cart.repository.CartDao;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartDao cartDao;

    public long add(long memberId, long productId) {
        return cartDao.add(memberId, productId);
    }

    public void delete(final long cartId) {
        cartDao.deleteById(cartId);
    }

    public List<CartItemDto> findAllByMemberId(long memberId) {
        return cartDao.findAllByMemberId(memberId).stream()
                .map(CartItemDto::from)
                .collect(Collectors.toList());
    }
}
