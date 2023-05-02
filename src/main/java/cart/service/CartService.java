package cart.service;

import cart.controller.dto.CartResponse;
import cart.controller.dto.ItemResponse;
import cart.dao.CartDao;
import cart.dao.dto.ItemDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CartService {

    private final CartDao cartDao;

    public CartService(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    public CartResponse findAllByMemberId(Long memberId) {
        List<ItemDto> itemDtos = cartDao.findAllByMemberId(memberId);

        List<ItemResponse> itemResponses = itemDtos.stream()
                .map(ItemResponse::from)
                .collect(Collectors.toList());
        return new CartResponse(memberId, itemResponses);
    }

    @Transactional
    public CartResponse save(Long memberId, Long itemId) {
        if (cartDao.findByMemberIdAndItemId(memberId, itemId).isPresent()) {
            throw new IllegalArgumentException("이미 장바구니에 추가된 상품입니다.");
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
