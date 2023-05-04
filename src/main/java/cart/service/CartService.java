package cart.service;

import cart.controller.dto.ProductResponse;
import cart.dao.CartDao;
import cart.entity.CartEntity;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartDao cartDao;

    public CartService(final CartDao cartDao) {
        this.cartDao = cartDao;
    }

    public Long addProduct(Long memberId, Long productId) {
        return cartDao.save(new CartEntity(memberId, productId));
    }

    public List<ProductResponse> findProductsByMemberId(Long id) {
        return cartDao.findProductsByMemberId(id)
                .stream()
                .map(ProductResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }
}
