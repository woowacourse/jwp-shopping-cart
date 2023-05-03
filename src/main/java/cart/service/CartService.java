package cart.service;

import cart.dao.CartDao;
import cart.domain.dto.CartDto;
import cart.entity.CartEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartDao cartDao;


    public CartService(final CartDao cartDao) {
        this.cartDao = cartDao;
    }

    public void addProduct(final Long productId, final Long member) {
        final CartEntity cartEntity = new CartEntity(productId, member);

        cartDao.save(cartEntity);
    }

    public void delete(final Long memberId, final Long productId) {
        cartDao.deleteById(memberId, productId);
    }

    public List<CartDto> getProducts(final Long memberId) {
        final List<CartEntity> cartEntities = cartDao.findByMemberId(memberId);

        return cartEntities.stream()
                .map(cart -> new CartDto(cart.getId(), cart.getProductId(), cart.getMemberId()))
                .collect(Collectors.toList());
    }
}
