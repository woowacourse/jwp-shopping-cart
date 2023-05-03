package cart.controller;

import cart.entity.item.CartItem;
import cart.entity.member.Member;
import cart.entity.product.Product;
import cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    public CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> findItems(HttpServletRequest request) {
        final Member authenticatedMember = (Member) request.getAttribute("authenticatedMember");
        final List<Product> products = cartService.findCartItems(authenticatedMember.getId());
        return ResponseEntity.ok().body(products);
    }

    @PostMapping("/{productId}")
    public ResponseEntity<CartItem> addItem(@PathVariable("productId") long productId, HttpServletRequest request) {
        final Member authenticatedMember = (Member) request.getAttribute("authenticatedMember");
        final CartItem addedItem = cartService.addItem(authenticatedMember.getId(), productId);
        return ResponseEntity.ok().body(addedItem);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteItem(@PathVariable("productId") long productId, HttpServletRequest request) {
        final Member authenticatedMember = (Member) request.getAttribute("authenticatedMember");
        cartService.deleteItem(authenticatedMember.getId(), productId);
        return ResponseEntity.noContent().build();
    }
}
