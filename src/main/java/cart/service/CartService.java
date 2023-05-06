package cart.service;

import cart.repository.CartDao;
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
}
