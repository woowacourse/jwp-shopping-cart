package cart.controller;

import cart.controller.dto.ItemRequest;
import cart.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/items")
    public ResponseEntity addItem(@RequestBody final ItemRequest itemRequest) {
        itemService.saveItem(itemRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
