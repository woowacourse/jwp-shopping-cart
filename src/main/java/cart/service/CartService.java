package cart.service;

import cart.controller.dto.CartResponse;
import cart.controller.dto.ItemResponse;
import cart.dao.CartDao;
import cart.dao.entity.ItemEntity;
import cart.exception.CartDuplicateException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CartService {

    private static final String CART_DUPLICATE_MESSAGE = "이미 장바구니에 추가된 상품입니다.";
    private final CartDao cartDao;

    public CartService(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    public List<ItemResponse> findAllByMemberId(Long memberId) {
        List<ItemEntity> itemEntities = cartDao.findAllByMemberId(memberId);

        return itemEntities.stream()
                .map(ItemResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public CartResponse save(Long memberId, Long itemId) {
        if (cartDao.findByMemberIdAndItemId(memberId, itemId).isPresent()) {
            throw new CartDuplicateException(CART_DUPLICATE_MESSAGE);
        }

        Long savedId = cartDao.insert(memberId, itemId);
        return new CartResponse(savedId, cartDao.findAllByMemberId(memberId)
                .stream()
                .map(ItemResponse::from)
                .collect(Collectors.toList()));
    }

    @Transactional
    public void delete(Long memberId, Long itemId) {
        cartDao.delete(memberId, itemId);
    }
}
