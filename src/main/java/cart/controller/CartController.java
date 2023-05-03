package cart.controller;

import cart.entity.item.CartItem;
import cart.entity.member.Member;
import cart.entity.product.Product;
import cart.service.cart.CartItemAddService;
import cart.service.cart.CartItemDeleteService;
import cart.service.cart.CartItemFindService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartItemAddService addService;
    private final CartItemFindService findService;
    private final CartItemDeleteService deleteService;

    public CartController(
            final CartItemAddService addService,
            final CartItemFindService findService,
            final CartItemDeleteService deleteService) {
        this.addService = addService;
        this.findService = findService;
        this.deleteService = deleteService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> findItems(HttpServletRequest request) {
        final Member authenticatedMember = (Member) request.getAttribute("authenticatedMember");
        final List<Product> products = findService.findCartItems(authenticatedMember.getId());
        return ResponseEntity.ok().body(products);
    }

    @PostMapping("/{productId}")
    public ResponseEntity<CartItem> addItem(@PathVariable("productId") long productId, HttpServletRequest request) {
        final Member authenticatedMember = (Member) request.getAttribute("authenticatedMember");
        final CartItem addedItem = addService.addItem(authenticatedMember.getId(), productId);
        return ResponseEntity.ok().body(addedItem);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteItem(@PathVariable("productId") long productId, HttpServletRequest request) {
        final Member authenticatedMember = (Member) request.getAttribute("authenticatedMember");
        deleteService.deleteItem(authenticatedMember.getId(), productId);
        return ResponseEntity.noContent().build();
    }
}
