package cart.controller.rest;

import cart.auth.AuthResolving;
import cart.controller.exception.UncertifiedMemberException;
import cart.dto.auth.AuthInfo;
import cart.dto.response.CartResponse;
import cart.dto.response.ItemResponse;
import cart.service.CartService;
import cart.service.MembersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.NotNull;
import java.net.URI;

@RestController
@RequestMapping("/api/cart")
public class CartsController {

    private final CartService cartService;
    private final MembersService membersService;

    public CartsController(CartService cartService, MembersService membersService) {
        this.cartService = cartService;
        this.membersService = membersService;
    }

    @PostMapping("/items")
    public ResponseEntity<ItemResponse> createItem(
            @AuthResolving AuthInfo authInfo,
            @RequestParam("product-id") Long productId) {

        validateAuthorization(authInfo);

        Long memberId = membersService.readIdByEmail(authInfo.getEmail());
        ItemResponse itemResponse = cartService.createItem(memberId, productId);

        URI createdUri = ServletUriComponentsBuilder
                .fromPath("/items/{id}")
                .buildAndExpand(itemResponse.getId())
                .toUri();

        return ResponseEntity.created(createdUri).body(itemResponse);
    }

    @GetMapping("/items")
    public ResponseEntity<CartResponse> readItemsByMember(@AuthResolving AuthInfo authInfo) {
        validateAuthorization(authInfo);

        Long memberId = membersService.readIdByEmail(authInfo.getEmail());

        CartResponse cartResponse = cartService.readAllItemsByMemberId(memberId);
        return ResponseEntity.ok().body(cartResponse);
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> deleteItem(
            @AuthResolving AuthInfo authInfo,
            @PathVariable("id") @NotNull Long itemId) {

        validateAuthorization(authInfo);

        cartService.deleteItemById(itemId);

        return ResponseEntity.noContent().build();
    }

    private void validateAuthorization(AuthInfo authInfo) {
        if (!membersService.isMemberCertified(authInfo)) {
            throw new UncertifiedMemberException();
        }
    }
}
