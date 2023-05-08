package cart.service;

import cart.controller.dto.request.UpdateItemRequest;
import cart.domain.item.Item;
import cart.repository.ItemRepository;
import cart.service.dto.ItemDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(final ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Transactional
    public ItemDto add(String name, String imageUrl, int price) {
        Item item = new Item(name, imageUrl, price);
        Item insertedItem = itemRepository.insert(item);

        return ItemDto.from(insertedItem);
    }

    public List<ItemDto> findAll() {
        return itemRepository.findAll()
                .stream()
                .map(ItemDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public ItemDto update(Long id, UpdateItemRequest updateItemRequest) {
        Item updateItem = new Item(id, updateItemRequest.getName(), updateItemRequest.getImageUrl(),
                updateItemRequest.getPrice());

        itemRepository.update(updateItem);

        return ItemDto.from(updateItem);
    }

    @Transactional
    public void delete(Long id) {
        itemRepository.delete(id);
    }
}
