package cart.service;

import cart.dao.cart.CartDao;
import cart.dao.item.ItemDao;
import cart.dto.cart.CartResponse;
import cart.dto.item.ItemResponse;
import cart.entity.ItemEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartDao cartDao;
    private final ItemDao itemDao;

    public CartService(final CartDao cartDao, final ItemDao itemDao) {
        this.cartDao = cartDao;
        this.itemDao = itemDao;
    }

    public ItemEntity save(String memberEmail, Long itemId) {
        return cartDao.save(memberEmail, itemDao.findById(itemId));
    }

    public List<CartResponse> findAll(String memberEmail) {
        Optional<Map<ItemEntity, Long>> cart = cartDao.findAll(memberEmail);

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
        cartDao.delete(memberEmail, itemDao.findById(itemId));
    }
}
