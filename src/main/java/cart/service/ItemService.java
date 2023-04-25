package cart.service;

import cart.controller.dto.ItemRequest;
import cart.controller.dto.ItemResponse;
import cart.model.Item;
import cart.repository.ItemDao;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ItemService {

    private final ItemDao itemDao;

    public ItemService(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    @Transactional
    public ItemResponse add(ItemRequest itemRequest) {
        Item item = new Item(itemRequest.getName(), itemRequest.getImageUrl(), itemRequest.getPrice());
        Long savedId = itemDao.insert(item);

        return ItemResponse.from(itemDao.findById(savedId));
    }

    public List<ItemResponse> findAll() {
        return itemDao.findAll()
                .stream()
                .map(ItemResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public ItemResponse update(Long id, ItemRequest itemRequest) {
        Item item = new Item(itemRequest.getName(), itemRequest.getImageUrl(), itemRequest.getPrice());
        itemDao.update(id, item);

        return ItemResponse.from(itemDao.findById(id));
    }

    @Transactional
    public void delete(Long id) {
        itemDao.delete(id);
    }
}
