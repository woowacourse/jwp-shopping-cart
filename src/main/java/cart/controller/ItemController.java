package cart.controller;

import cart.domain.Item;
import cart.dto.*;
import cart.dto.item.ItemResponse;
import cart.dto.item.ItemSaveRequest;
import cart.dto.item.ItemUpdateRequest;
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
        ItemResponse saveItem = itemService.save(itemSaveRequest.toItem());
        return new ResultResponse(SuccessCode.CREATE_ITEM, saveItem);
    }

    @PutMapping
    public ResultResponse editItem(@Valid @RequestBody ItemUpdateRequest itemUpdateRequest) {
        Long itemId = itemUpdateRequest.getId();
        Item item = itemUpdateRequest.toItem();

        itemService.updateItem(itemId, item);
        return new ResultResponse(SuccessCode.UPDATE_ITEM, item);
    }

    @DeleteMapping("/{itemId}")
    public ResultResponse deleteItem(@PathVariable Long itemId) {
        itemService.deleteItem(itemId);
        return new ResultResponse(SuccessCode.DELETE_ITEM, itemId);
    }
}
