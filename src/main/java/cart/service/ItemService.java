package cart.service;

import cart.controller.dto.ItemRequest;
import cart.controller.dto.ItemResponse;
import cart.dao.ItemDao;
import cart.domain.ImageUrl;
import cart.domain.Item;
import cart.domain.Name;
import cart.domain.Price;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemService {

    private final ItemDao itemDao;

    public ItemService(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    @Transactional(readOnly = true)
    public List<ItemResponse> loadAllItem() {
        List<Item> allItem = itemDao.findAll();
        return allItem.stream()
                      .map(ItemResponse::from)
                      .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ItemResponse loadItem(final Long itemId) {
        Optional<Item> findItem = itemDao.findBy(itemId);
        Item item = findItem.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이템 입니다."));
        return ItemResponse.from(item);
    }

    @Transactional
    public Long saveItem(final ItemRequest itemRequest) {
        Item item = new Item.Builder()
                .name(new Name(itemRequest.getName()))
                .imageUrl(new ImageUrl(itemRequest.getImageUrl()))
                .price(new Price(itemRequest.getPrice()))
                .build();
        return itemDao.save(item);
    }

    @Transactional
    public void updateItem(final Long itemId, final ItemRequest itemRequest) {
        validateExistItem(itemId);
        Item item = new Item.Builder()
                .id(itemId)
                .name(new Name(itemRequest.getName()))
                .imageUrl(new ImageUrl(itemRequest.getImageUrl()))
                .price(new Price(itemRequest.getPrice()))
                .build();
        itemDao.update(item);
    }

    @Transactional
    public void deleteItem(final Long itemId) {
        validateExistItem(itemId);
        itemDao.deleteBy(itemId);
    }

    private void validateExistItem(Long itemId) {
        Optional<Item> findItem = itemDao.findBy(itemId);
        if (findItem.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 아이템 입니다.");
        }
    }
}
