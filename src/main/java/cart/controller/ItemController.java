package cart.controller;

import cart.controller.dto.ItemRequest;
import cart.dao.entity.Item;
import cart.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("items")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity addItem(@RequestBody final ItemRequest itemRequest) {
        itemService.saveItem(itemRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<Item>> loadItem() {
        List<Item> items = itemService.loadAllItem();
        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(items);
    }

    @PutMapping("/{itemId}")
    public ResponseEntity updateItem(@PathVariable final Long itemId,
                                     @RequestBody final ItemRequest itemRequest) {
        itemService.updateItem(itemId, itemRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity deleteItem(@PathVariable final Long itemId) {
        itemService.deleteItem(itemId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
