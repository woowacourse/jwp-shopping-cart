package cart.controller;

import cart.auth.AuthenticationExtractor;
import cart.auth.AuthenticationService;
import cart.auth.MemberAuthentication;
import cart.dto.CartItemDto;
import cart.dto.MemberDto;
import cart.dto.request.CartItemCreationRequest;
import cart.exception.AuthenticationException;
import cart.service.CartItemManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

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
        if (memberAuthentication == null) {
            throw new AuthenticationException("사용자 인증이 필요합니다.");
        }
        MemberDto memberDto = authenticationService.login(memberAuthentication);

        final Long productId = cartItemCreationRequest.getProductId();
        final long id = cartItemManagementService.save(CartItemDto.of(memberDto.getId(), productId));
        return ResponseEntity.created(URI.create("/cart/cart-items/" + id)).build();
    }
}
