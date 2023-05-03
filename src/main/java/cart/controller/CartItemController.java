package cart.controller;

import cart.auth.AuthenticationExtractor;
import cart.auth.AuthenticationService;
import cart.auth.MemberAuthentication;
import cart.dto.CartAdditionDto;
import cart.dto.MemberDto;
import cart.dto.request.CartItemCreationRequest;
import cart.dto.response.CartItemResponse;
import cart.service.CartItemManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cart/cart-items")
public class CartItemController {

    private final AuthenticationExtractor<MemberAuthentication> authenticationExtractor;
    private final AuthenticationService authenticationService;
    private final CartItemManagementService cartItemManagementService;

    public CartItemController(
            final AuthenticationExtractor<MemberAuthentication> authenticationExtractor,
            final AuthenticationService authenticationService,
            final CartItemManagementService cartItemManagementService
    ) {
        this.authenticationExtractor = authenticationExtractor;
        this.authenticationService = authenticationService;
        this.cartItemManagementService = cartItemManagementService;
    }

    @PostMapping
    public ResponseEntity<Void> postCartItems(HttpServletRequest request, @RequestBody CartItemCreationRequest cartItemCreationRequest) {
        MemberAuthentication memberAuthentication = authenticationExtractor.extract(request);
        MemberDto memberDto = authenticationService.login(memberAuthentication);

        final Long productId = cartItemCreationRequest.getProductId();
        final long id = cartItemManagementService.save(CartAdditionDto.of(memberDto.getId(), productId));
        return ResponseEntity.created(URI.create("/cart/cart-items/" + id)).build();
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> getCartItems(HttpServletRequest request) {
        MemberAuthentication memberAuthentication = authenticationExtractor.extract(request);
        MemberDto memberDto = authenticationService.login(memberAuthentication);

        List<CartItemResponse> response = CartItemResponse.from(cartItemManagementService.findAllByMemberId(memberDto.getId()));
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> deleteCartItems(HttpServletRequest request, @PathVariable Long cartItemId) {
        MemberAuthentication memberAuthentication = authenticationExtractor.extract(request);
        MemberDto memberDto = authenticationService.login(memberAuthentication);

        cartItemManagementService.deleteById(cartItemId);
        return ResponseEntity.noContent().build();
    }
}
