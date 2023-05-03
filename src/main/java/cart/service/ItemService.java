package cart.service;

import cart.controller.dto.ItemRequest;
import cart.controller.dto.ItemResponse;
import cart.dao.ItemDao;
import cart.dao.entity.ItemEntity;
import cart.exception.DataBaseSearchException;
import cart.exception.ItemNotFoundException;
import cart.domain.Item;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ItemService {

    private static final String DB_ERROR_MESSAGE = "데이터베이스에서 값을 가져오기를 실패했습니다.";
    private static final String ITEM_NOT_FOUND_MESSAGE = "일치하는 상품을 찾을 수 없습니다.";

    private final ItemDao itemDao;

    public ItemService(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    @Transactional
    public ItemResponse add(ItemRequest itemRequest) {
        Item item = new Item(itemRequest.getName(), itemRequest.getImageUrl(), itemRequest.getPrice());
        Long savedId = itemDao.insert(item);

        ItemEntity itemEntity = itemDao.findById(savedId)
                .orElseThrow(() -> new DataBaseSearchException(DB_ERROR_MESSAGE));

        return ItemResponse.from(itemEntity);
    }

    public List<ItemResponse> findAll() {
        return itemDao.findAll()
                .stream()
                .map(ItemResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public ItemResponse update(Long id, ItemRequest itemRequest) {
        validateId(id);

        Item item = new Item(itemRequest.getName(), itemRequest.getImageUrl(), itemRequest.getPrice());
        itemDao.update(id, item);

        ItemEntity updatedItemEntity = itemDao.findById(id)
                .orElseThrow(() -> new DataBaseSearchException(DB_ERROR_MESSAGE));
        return ItemResponse.from(updatedItemEntity);
    }

    @Transactional
    public void delete(Long id) {
        validateId(id);

        itemDao.delete(id);
    }

    private void validateId(Long id) {
        if (itemDao.findById(id).isEmpty()) {
            throw new ItemNotFoundException(ITEM_NOT_FOUND_MESSAGE);
        }
    }
}
