package cart.controller;

import cart.dto.ResultResponse;
import cart.dto.SuccessCode;
import cart.dto.item.ItemResponse;
import cart.dto.item.ItemSaveRequest;
import cart.dto.item.ItemUpdateRequest;
import cart.entity.ItemEntity;
import cart.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin/item")
public class ItemController {

    private final ItemService itemService;

    public ItemController(final ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResultResponse addItem(@Valid @RequestBody ItemSaveRequest itemSaveRequest) {
        ItemEntity itemEntity = new ItemEntity(itemSaveRequest.getName(),
                itemSaveRequest.getImageUrl(),
                itemSaveRequest.getPrice());

        ItemResponse saveItem = itemService.save(itemEntity);

        return new ResultResponse(SuccessCode.CREATE_ITEM, saveItem);
    }

    @PutMapping
    public ResultResponse editItem(@Valid @RequestBody ItemUpdateRequest itemUpdateRequest) {
        Long itemId = itemUpdateRequest.getId();
        ItemEntity item = new ItemEntity(itemUpdateRequest.getId(),
                itemUpdateRequest.getName(),
                itemUpdateRequest.getImageUrl(),
                itemUpdateRequest.getPrice());

        itemService.update(itemId, item);
        return new ResultResponse(SuccessCode.UPDATE_ITEM, item);
    }

    @DeleteMapping("/{itemId}")
    public ResultResponse deleteItem(@PathVariable Long itemId) {
        itemService.delete(itemId);
        return new ResultResponse(SuccessCode.DELETE_ITEM, itemId);
    }
}
