package cart.service;

import cart.controller.dto.AddItemRequest;
import cart.controller.dto.ItemResponse;
import cart.controller.dto.UpdateItemRequest;
import cart.dao.ItemDao;
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

    private static final int CORRECT_ROW_COUNT = 1;
    private final ItemDao itemDao;

    public ItemService(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    @Transactional
    public ItemResponse add(AddItemRequest addItemRequest) {
        Item item = new Item(addItemRequest.getName(), addItemRequest.getImageUrl(), addItemRequest.getPrice());
        Item savedItem = itemDao.insert(item);

        return ItemResponse.from(savedItem);
    }

    public List<ItemResponse> findAll() {
        return itemDao.findAll()
                .stream()
                .map(ItemResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public ItemResponse update(Long id, UpdateItemRequest updateItemRequest) {
        Item updateItem = new Item(id, updateItemRequest.getName(), updateItemRequest.getImageUrl(),
                updateItemRequest.getPrice());

        int updateRecordCount = itemDao.update(updateItem);

        if (updateRecordCount != CORRECT_ROW_COUNT) {
            throw new ItemException(ErrorStatus.ITEM_NOT_FOUND_ERROR);
        }

        return ItemResponse.from(updateItem);
    }

    @Transactional
    public void delete(Long id) {
        int deleteRecordCount = itemDao.delete(id);

        if (deleteRecordCount != CORRECT_ROW_COUNT) {
            throw new ItemException(ErrorStatus.ITEM_NOT_FOUND_ERROR);
        }
    }
}
