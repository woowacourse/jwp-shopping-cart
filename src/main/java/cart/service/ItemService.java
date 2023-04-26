package cart.service;

import cart.dao.ItemDao;
import cart.dto.ItemRequest;
import cart.dto.ItemResponse;
import cart.entity.CreateItem;
import cart.entity.Item;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    private final ItemDao itemDao;

    public ItemService(final ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    public void save(ItemRequest itemRequest) {
        CreateItem createItem = convertItemRequestToCreateItem(itemRequest);
        itemDao.save(createItem);
    }

    private CreateItem convertItemRequestToCreateItem(final ItemRequest itemRequest) {
        return new CreateItem(itemRequest.getName(), itemRequest.getImageUrl(), itemRequest.getPrice());
    }

    public List<ItemResponse> findAll() {
        List<Item> items = itemDao.findAll();
        return convertItemsToItemResponses(items);
    }

    private List<ItemResponse> convertItemsToItemResponses(final List<Item> items) {
        return items.stream()
                .map(item -> new ItemResponse(item.getId(), item.getName(), item.getImageUrl(), item.getPrice()))
                .collect(Collectors.toList());
    }

    public void updateItem(Long itemId, ItemRequest itemRequest) {
        CreateItem createItem = convertItemRequestToCreateItem(itemRequest);
        itemDao.update(itemId, createItem);
    }

    public void deleteItem(Long itemId) {
        itemDao.delete(itemId);
    }
}
