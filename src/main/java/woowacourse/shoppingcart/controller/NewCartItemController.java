package woowacourse.shoppingcart.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.dto.CartItemRequest;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.service.CartItemService;

@RestController
@RequestMapping("/api/mycarts")
public class NewCartItemController {

    private final CartItemService cartItemService;

    public NewCartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping
    public ResponseEntity<CartItemResponse> addCart(@AuthenticationPrincipal String email,
        @Valid @RequestBody CartItemRequest cartItemRequest) {
        CartItemResponse cartItemResponse = cartItemService.addCart(email, cartItemRequest);
        return ResponseEntity.created(URI.create("/api/mycarts/" + cartItemResponse.getId())).body(cartItemResponse);
    }
}
