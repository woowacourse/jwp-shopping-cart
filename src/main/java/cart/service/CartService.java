package cart.service;

import static java.util.stream.Collectors.toList;

import cart.dao.CartDao;
import cart.domain.Cart;
import cart.dto.CartSaveRequest;
import cart.dto.CartSearchResponse;
import cart.dto.ProductDto;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CartService {

    private final CartDao cartDao;

    public CartService(final CartDao cartDao) {
        this.cartDao = cartDao;
    }

    public Long save(final Long memberId, final CartSaveRequest request) {
        final Cart cart = new Cart(memberId, request.getProductId());
        return cartDao.saveAndGetId(cart);
    }

    @Transactional(readOnly = true)
    public CartSearchResponse findAll(final Long memberId) {
        return cartDao.findAllProductByMemberId(memberId).stream()
                .map(ProductDto::from)
                .collect(Collectors.collectingAndThen(toList(), CartSearchResponse::new));
    }
}
