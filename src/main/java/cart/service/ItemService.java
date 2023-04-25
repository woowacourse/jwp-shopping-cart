package cart.service;

import cart.controller.dto.ItemRequest;
import cart.dao.ItemDao;
import cart.dao.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final ItemDao itemDao;

    @Autowired
    public ItemService(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    public List<Item> loadAllItem() {
        return itemDao.findAll();
    }

    public void saveItem(final ItemRequest itemRequest) {
        Item item = new Item.Builder()
                .name(itemRequest.getName())
                .imageUrl(itemRequest.getImageUrl())
                .price(itemRequest.getPrice())
                .build();
        itemDao.save(item);
    }

    public void updateItem(final Long itemId, final ItemRequest itemRequest) {
        Item item = new Item.Builder()
                .id(itemId)
                .name(itemRequest.getName())
                .imageUrl(itemRequest.getImageUrl())
                .price(itemRequest.getPrice())
                .build();
        itemDao.update(item);
    }

    public void deleteItem(final Long itemId) {
        itemDao.deleteBy(itemId);
    }
}
