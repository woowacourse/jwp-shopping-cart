package cart.controller;

import cart.controller.dto.ItemRequest;
import cart.controller.dto.ItemResponse;
import cart.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity addItem(@RequestBody @Validated final ItemRequest itemRequest) {
        itemService.saveItem(itemRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .location(URI.create("/"))
                             .build();
    }

    @GetMapping
    public ResponseEntity<List<ItemResponse>> loadAllItem() {
        List<ItemResponse> items = itemService.loadAllItem();
        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(items);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemResponse> loadItem(@PathVariable final Long itemId) {
        ItemResponse item = itemService.loadItem(itemId);
        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(item);
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<Void> updateItem(@PathVariable final Long itemId,
                                     @RequestBody @Validated final ItemRequest itemRequest) {
        itemService.updateItem(itemId, itemRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .location(URI.create("/"))
                             .build();
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable final Long itemId) {
        itemService.deleteItem(itemId);
        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create("/"))
                             .build();
    }
}
