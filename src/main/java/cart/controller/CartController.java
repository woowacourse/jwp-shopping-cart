package cart.controller;

import cart.argumentresolver.AuthenticatedMember;
import cart.dto.CartItemResponse;
import cart.dto.ProductResponse;
import cart.entity.item.CartItem;
import cart.entity.member.Member;
import cart.service.cart.CartItemAddService;
import cart.service.cart.CartItemDeleteService;
import cart.service.cart.CartItemFindService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartItemAddService addService;
    private final CartItemFindService findService;
    private final CartItemDeleteService deleteService;

    public CartController(final CartItemAddService addService, final CartItemFindService findService, final CartItemDeleteService deleteService) {
        this.addService = addService;
        this.findService = findService;
        this.deleteService = deleteService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductResponse>> findItems(@AuthenticatedMember Member authenticatedMember) {
        final List<ProductResponse> products = findService.findCartItems(authenticatedMember.getId()).stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(products);
    }

    @PostMapping("/{productId}")
    public ResponseEntity<CartItemResponse> addItem(@PathVariable("productId") long productId, @AuthenticatedMember Member authenticatedMember) {
        final CartItem addedItem = addService.addItem(authenticatedMember.getId(), productId);
        final CartItemResponse cartItem = CartItemResponse.from(addedItem);
        return ResponseEntity.ok().body(cartItem);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteItem(@PathVariable("productId") long productId, @AuthenticatedMember Member authenticatedMember) {
        deleteService.deleteItem(authenticatedMember.getId(), productId);
        return ResponseEntity.noContent().build();
    }
}
