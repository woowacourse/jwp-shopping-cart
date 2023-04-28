package cart.service;

import cart.controller.dto.UpdateItemRequest;
import cart.dao.ItemDao;
import cart.exception.item.ItemNotFoundException;
import cart.domain.item.Item;
import cart.service.dto.ItemDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ItemService {

    private static final String NOT_FOUND_MESSAGE = "일치하는 상품을 찾을 수 없습니다.";
    private static final int CORRECT_ROW_COUNT = 1;

    private final ItemDao itemDao;

    public ItemService(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    @Transactional
    public ItemDto add(String name, String imageUrl, int price) {
        Item item = new Item(name, imageUrl, price);
        Item savedItem = itemDao.insert(item);

        return new ItemDto(savedItem);
    }

    public List<ItemDto> findAll() {
        return itemDao.findAll()
                .stream()
                .map(ItemDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public ItemDto update(Long id, UpdateItemRequest updateItemRequest) {
        Item updateItem = new Item(id, updateItemRequest.getName(), updateItemRequest.getImageUrl(),
                updateItemRequest.getPrice());

        int updateRecordCount = itemDao.update(updateItem);

        if (updateRecordCount != CORRECT_ROW_COUNT) {
            throw new ItemNotFoundException(NOT_FOUND_MESSAGE);
        }

        return new ItemDto(updateItem);
    }

    @Transactional
    public void delete(Long id) {
        int deleteRecordCount = itemDao.delete(id);

        if (deleteRecordCount != CORRECT_ROW_COUNT) {
            throw new ItemNotFoundException(NOT_FOUND_MESSAGE);
        }
    }
}
