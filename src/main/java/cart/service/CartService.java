package cart.service;

import cart.dao.CartDao;
import cart.dao.ProductDao;
import cart.service.dto.CartRequest;
import cart.service.dto.CartResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartDao cartDao;
    private final ProductDao productDao;

    public CartService(final CartDao cartDao, final ProductDao productDao) {
        this.cartDao = cartDao;
        this.productDao = productDao;
    }

    public long save(final CartRequest cartRequest, final long customerId) {
        return cartDao.insert(customerId, cartRequest.getProductId());
    }

    public List<CartResponse> findAllByCustomerId(final long customerId) {
        return cartDao.findAllCartProductByCustomerId(customerId)
                .stream()
                .map(CartResponse::from)
                // 근데 이러면 조회할 필드가 바뀌면 서비스 바뀌고 + dao도 바뀌는거 아닌가? 그러면 dao에서는 그냥 전체값을 조회해오는게 낫나?
                .collect(Collectors.toList());
    }

}
