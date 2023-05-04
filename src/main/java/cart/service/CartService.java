package cart.service;

import cart.annotation.ServiceWithTransactionalReadOnly;
import cart.controller.dto.request.ProductIdRequest;
import cart.controller.dto.response.CartItemResponse;
import cart.dao.CartDao;
import cart.dao.ProductDao;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@ServiceWithTransactionalReadOnly
public class CartService {

    private final CartDao cartDao;
    private final ProductDao productDao;

    public CartService(CartDao cartDao, ProductDao productDao) {
        this.cartDao = cartDao;
        this.productDao = productDao;
    }

    @Transactional
    public void save(final Long memberId, final ProductIdRequest request) {
        cartDao.save(memberId, request.getProductId());
    }

    public List<CartItemResponse> findAll(Long memberId) {
        return cartDao.findAllByMemberId(memberId).stream()
                .map(CartItemResponse::from)
                .collect(Collectors.toList());
    }

}
