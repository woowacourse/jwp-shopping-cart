package cart.controller;

import cart.controller.dto.AddItemRequest;
import cart.controller.dto.ItemResponse;
import cart.controller.dto.UpdateItemRequest;
import cart.service.ItemService;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<List<ItemResponse>> findAllItems() {
        List<ItemResponse> itemResponses = itemService.findAll();
        return ResponseEntity.ok(itemResponses);
    }

    @PostMapping
    public ResponseEntity<ItemResponse> addItem(@RequestBody @Valid AddItemRequest addItemRequest) {
        ItemResponse itemResponse = itemService.add(addItemRequest);
        return ResponseEntity.created(URI.create("/items/" + itemResponse.getId()))
                .body(itemResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemResponse> updateItem(
            @RequestBody @Valid UpdateItemRequest updateItemRequest,
            @PathVariable Long id
    ) {
        ItemResponse itemResponse = itemService.update(id, updateItemRequest);
        return ResponseEntity.ok(itemResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        itemService.delete(id);
        return ResponseEntity.noContent()
                .build();
    }
}
