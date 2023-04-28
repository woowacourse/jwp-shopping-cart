package cart.service;

import cart.dao.ItemDao;
import cart.dto.ItemResponse;
import cart.domain.Item;
import cart.entity.ItemEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    private final ItemDao itemDao;

    public ItemService(final ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    public void save(Item item) {
        itemDao.save(item);
    }

    public List<ItemResponse> findAll() {
        List<ItemEntity> itemEntities = itemDao.findAll();
        return convertItemsToItemResponses(itemEntities);
    }

    private List<ItemResponse> convertItemsToItemResponses(final List<ItemEntity> itemEntities) {
        return itemEntities.stream()
                .map(itemEntity -> new ItemResponse(itemEntity.getId(), itemEntity.getName(), itemEntity.getImageUrl(), itemEntity.getPrice()))
                .collect(Collectors.toList());
    }

    public void updateItem(Long itemId, Item item) {
        itemDao.update(itemId, item);
    }

    public void deleteItem(Long itemId) {
        itemDao.delete(itemId);
    }
}
