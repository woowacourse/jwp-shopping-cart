package cart.service;

import cart.controller.dto.ItemRequest;
import cart.controller.dto.ItemResponse;
import cart.dao.ItemDao;
import cart.dao.dto.ItemDto;
import cart.exception.ErrorStatus;
import cart.exception.ItemException;
import cart.model.Item;
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

        ItemDto itemDto = itemDao.findById(savedId)
                .orElseThrow(() -> new ItemException(ErrorStatus.ITEM_NOT_FOUND_ERROR));

        return ItemResponse.from(itemDto);
    }

    public List<ItemResponse> findAll() {
        return itemDao.findAll()
                .stream()
                .map(ItemResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public ItemResponse update(Long id, ItemRequest itemRequest) {
        itemDao.findById(id)
                .orElseThrow(() -> new ItemException(ErrorStatus.ITEM_NOT_FOUND_ERROR));

        Item item = new Item(itemRequest.getName(), itemRequest.getImageUrl(), itemRequest.getPrice());
        itemDao.update(id, item);

        ItemDto updatedItemDto = itemDao.findById(id)
                .orElseThrow(() -> new ItemException(ErrorStatus.ITEM_NOT_FOUND_ERROR));
        return ItemResponse.from(updatedItemDto);
    }

    @Transactional
    public void delete(Long id) {
        itemDao.findById(id)
                .orElseThrow(() -> new ItemException(ErrorStatus.ITEM_NOT_FOUND_ERROR));

        itemDao.delete(id);
    }
}
