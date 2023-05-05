package cart.service;

import cart.dao.member.MemberCartDao;
import cart.dto.cart.CartResponse;
import cart.dto.item.ItemResponse;
import cart.entity.ItemEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final MemberCartDao memberCartDao;

    public Long save(String memberEmail, Long itemId) {
        return memberCartDao.save(memberEmail, itemId);
    }

    public List<CartResponse> findAll(String memberEmail) {
        Optional<Map<ItemEntity, Long>> cart = memberCartDao.findAll(memberEmail);

        if(cart.isEmpty()){
            return Collections.emptyList();
        }

        return convertItemEntityMapToCartItemEntityList(cart.get());
    }

    private List<CartResponse> convertItemEntityMapToCartItemEntityList(final Map<ItemEntity, Long> itemEntities) {
        return itemEntities.entrySet()
                .stream()
                .map(item -> new CartResponse(itemEntityToItemResponse(item.getKey()), item.getValue()))
                .collect(Collectors.toList());
    }

    private ItemResponse itemEntityToItemResponse(final ItemEntity item) {
        return new ItemResponse(item.getId(),
                item.getName(),
                item.getImageUrl(),
                item.getPrice());
    }

    public void delete(String memberEmail, Long itemId) {
        memberCartDao.delete(memberEmail, itemId);
    }
}
