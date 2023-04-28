package cart.controller;

import cart.dto.ItemRequest;
import cart.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addItem(@Valid @RequestBody ItemRequest itemRequest) {
        itemService.save(itemRequest.toItem());
        return ResponseEntity.status(HttpStatus.CREATED).body("Item has been added successfully.");
    }

    @PutMapping("/item/{itemId}")
    public ResponseEntity<String> editItem(@PathVariable Long itemId, @Valid @RequestBody ItemRequest itemRequest) {
        itemService.updateItem(itemId, itemRequest.toItem());
        return ResponseEntity.ok("Item with ID " + itemId + " has been updated successfully.");
    }

    @DeleteMapping("/item/{itemId}")
    public ResponseEntity<String> deleteItem(@PathVariable Long itemId) {
        itemService.deleteItem(itemId);
        return ResponseEntity.ok("Item with ID " + itemId + " has been deleted successfully.");
    }
}
