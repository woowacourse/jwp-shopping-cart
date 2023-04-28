package cart.controller;

import cart.dto.ItemRequest;
import cart.dto.ItemUpdateRequest;
import cart.dto.ResultResponse;
import cart.dto.SuccessCode;
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
        itemService.save(itemRequest.toItem());
        return new ResultResponse(SuccessCode.CREATE_ITEM, itemRequest.toItem());
    }

    @ResponseBody
    @PutMapping("/item")
    public ResultResponse editItem(@Valid @RequestBody ItemUpdateRequest itemUpdateRequest) {
        Long itemId = itemUpdateRequest.getId();

        itemService.updateItem(itemId, itemUpdateRequest.toItem());
        return new ResultResponse(SuccessCode.UPDATE_ITEM, itemUpdateRequest.toItem());
    }

    @ResponseBody
    @DeleteMapping("/item/{itemId}")
    public ResultResponse deleteItem(@PathVariable Long itemId) {
        itemService.deleteItem(itemId);
        return new ResultResponse(SuccessCode.DELETE_ITEM, itemId);
    }
}
