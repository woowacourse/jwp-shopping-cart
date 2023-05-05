package cart.service.cart;

import cart.controller.dto.request.MemberIdRequest;
import cart.dao.CartDao;
import cart.domain.dto.CartDto;
import cart.entity.CartEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class CartReadService {
    private final CartDao cartDao;


    public CartReadService(final CartDao cartDao) {
        this.cartDao = cartDao;
    }

    public List<CartDto> getProducts(final MemberIdRequest memberId) {
        final List<CartEntity> cartEntities = cartDao.findByMemberId(memberId.getId());

        return cartEntities.stream()
                .map(cart -> new CartDto(cart.getId(), cart.getProductId(), cart.getMemberId()))
                .collect(Collectors.toList());
    }
}
