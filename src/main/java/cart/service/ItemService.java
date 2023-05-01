package cart.service;

import cart.dao.item.ItemDao;

import cart.dto.item.ItemResponse;
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

    public ItemResponse save(ItemEntity item) {
        ItemEntity save = itemDao.save(item);
        return new ItemResponse(save.getId(), save.getName(), save.getImageUrl(), save.getPrice());
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

    public void update(Long itemId, ItemEntity item) {
        itemDao.update(itemId, item);
    }

    public void delete(Long itemId) {
        itemDao.delete(itemId);
    }
}
