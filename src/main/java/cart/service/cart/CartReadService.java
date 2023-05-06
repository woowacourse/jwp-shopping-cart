package cart.service.cart;

import cart.controller.dto.request.MemberId;
import cart.dao.CarProductDao;
import cart.domain.dto.CartDto;
import cart.entity.CartProductEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class CartReadService {
    private final CarProductDao carProductDao;


    public CartReadService(final CarProductDao carProductDao) {
        this.carProductDao = carProductDao;
    }

    public List<CartDto> getProducts(final MemberId memberId) {
        final List<CartProductEntity> cartEntities = carProductDao.findByMemberId(memberId.getId());

        return cartEntities.stream()
                .map(cart -> new CartDto(cart.getId(), cart.getProductId(), cart.getMemberId()))
                .collect(Collectors.toList());
    }
}
