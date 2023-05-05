package cart.service;

import cart.controller.dto.request.CartItemCreationRequest;
import cart.controller.dto.request.CartItemDeleteRequest;
import cart.controller.dto.request.MemberIdRequest;
import cart.dao.CartDao;
import cart.domain.dto.CartDto;
import cart.entity.CartEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CartService {

    private final CartDao cartDao;


    public CartService(final CartDao cartDao) {
        this.cartDao = cartDao;
    }

    public void addProduct(final CartItemCreationRequest productId, final MemberIdRequest member) {
        final CartEntity cartEntity = new CartEntity(productId.getProductId(), member.getId());

        cartDao.save(cartEntity);
    }

    public void delete(final MemberIdRequest memberId, final CartItemDeleteRequest productId) {
        final CartEntity cartEntity = new CartEntity(productId.getId(), memberId.getId());

        cartDao.deleteById(cartEntity);
    }

    @Transactional(readOnly = true)
    public List<CartDto> getProducts(final MemberIdRequest memberId) {
        final List<CartEntity> cartEntities = cartDao.findByMemberId(memberId.getId());

        return cartEntities.stream()
                .map(cart -> new CartDto(cart.getId(), cart.getProductId(), cart.getMemberId()))
                .collect(Collectors.toList());
    }
}
