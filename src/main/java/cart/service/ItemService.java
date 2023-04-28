package cart.service;

import cart.controller.dto.AddItemRequest;
import cart.controller.dto.ItemResponse;
import cart.controller.dto.UpdateItemRequest;
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
    public ItemResponse add(AddItemRequest addItemRequest) {
        Item item = new Item(addItemRequest.getName(), addItemRequest.getImageUrl(), addItemRequest.getPrice());
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
    public ItemResponse update(Long id, UpdateItemRequest updateItemRequest) {
        if (itemDao.findById(id).isEmpty()) {
            throw new ItemException(ErrorStatus.ITEM_NOT_FOUND_ERROR);
        }

        Item item = new Item(updateItemRequest.getName(), updateItemRequest.getImageUrl(), updateItemRequest.getPrice());
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
