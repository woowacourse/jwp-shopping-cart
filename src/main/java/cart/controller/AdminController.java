package cart.controller;

import cart.domain.Item;
import cart.dto.*;
import cart.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ItemService itemService;

    public AdminController(final ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public String displayItemList(Model model) {
        model.addAttribute("products", itemService.findAll());
        return "admin";
    }

    @PostMapping("/item")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ResultResponse addItem(@Valid @RequestBody ItemRequest itemRequest) {
        ItemResponse saveItem = itemService.save(itemRequest.toItem());
        return new ResultResponse(SuccessCode.CREATE_ITEM, saveItem);
    }

    @ResponseBody
    @PutMapping("/item")
    public ResultResponse editItem(@Valid @RequestBody ItemUpdateRequest itemUpdateRequest) {
        Long itemId = itemUpdateRequest.getId();
        Item item = itemUpdateRequest.toItem();

        itemService.updateItem(itemId, item);
        return new ResultResponse(SuccessCode.UPDATE_ITEM, item);
    }

    @ResponseBody
    @DeleteMapping("/item/{itemId}")
    public ResultResponse deleteItem(@PathVariable Long itemId) {
        itemService.deleteItem(itemId);
        return new ResultResponse(SuccessCode.DELETE_ITEM, itemId);
    }
}
