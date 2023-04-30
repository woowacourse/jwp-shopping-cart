package cart.service;

import cart.dao.cart.CartDao;
import cart.dto.item.ItemResponse;
import cart.entity.ItemEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartDao cartDao;

    public CartService(final CartDao cartDao) {
        this.cartDao = cartDao;
    }

    public Long save(String memberEmail, Long itemId) {
        return cartDao.save(memberEmail, itemId);
    }

    public List<ItemResponse> findAll(String memberEmail) {
        List<ItemEntity> items = cartDao.findAll(memberEmail);
        return convertItemsToItemResponses(items);
    }

    private List<ItemResponse> convertItemsToItemResponses(final List<ItemEntity> itemEntities) {
        return itemEntities.stream()
                .map(itemEntity -> new ItemResponse(itemEntity.getId(), itemEntity.getName(), itemEntity.getImageUrl(), itemEntity.getPrice()))
                .collect(Collectors.toList());
    }

    public void delete(String memberEmail, Long itemId) {
        cartDao.delete(memberEmail, itemId);
    }
}
