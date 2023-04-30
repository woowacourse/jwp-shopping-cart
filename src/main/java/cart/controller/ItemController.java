package cart.controller;

import cart.controller.dto.request.AddItemRequest;
import cart.controller.dto.response.ItemResponse;
import cart.controller.dto.request.UpdateItemRequest;
import cart.service.ItemService;
import cart.service.dto.ItemDto;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
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
        List<ItemResponse> itemResponses = itemService.findAll()
                .stream()
                .map(ItemResponse::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(itemResponses);
    }

    @PostMapping
    public ResponseEntity<ItemResponse> addItem(@RequestBody @Valid AddItemRequest addItemRequest) {
        ItemDto itemDto = itemService.add(addItemRequest.getName(), addItemRequest.getImageUrl(),
                addItemRequest.getPrice());

        return ResponseEntity.created(URI.create("/items/" + itemDto.getId()))
                .body(ItemResponse.from(itemDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemResponse> updateItem(
            @RequestBody @Valid UpdateItemRequest updateItemRequest,
            @PathVariable Long id
    ) {
        ItemDto itemDto = itemService.update(id, updateItemRequest);

        return ResponseEntity.ok(ItemResponse.from(itemDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        itemService.delete(id);

        return ResponseEntity.noContent()
                .build();
    }
}
