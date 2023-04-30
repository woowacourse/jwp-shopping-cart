package cart.controller;

import cart.dto.ResultResponse;
import cart.dto.SuccessCode;
import cart.dto.cart.CartSaveRequest;
import cart.dto.item.ItemResponse;
import cart.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
public class CartController {

    private final CartService cartService;

    public CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/carts")
    public List<ItemResponse> getCarts(@RequestHeader(value = "Authorization") String authorization) {
        if (authorization != null && authorization.startsWith("Basic ")) {
            String base64Credentials = authorization.substring("Basic ".length());
            String memberEmail = new String(Base64Utils.decodeFromString(base64Credentials)).split(":")[0];
            return cartService.findAll(memberEmail);
        }
        return Collections.emptyList();
    }

    @PostMapping("/cart")
    public ResponseEntity<ResultResponse> addCart(@RequestHeader(value = "Authorization") String authorization, @RequestBody CartSaveRequest cart) {
        if (authorization != null && authorization.startsWith("Basic ")) {
            String base64Credentials = authorization.substring("Basic ".length());
            String memberEmail = new String(Base64Utils.decodeFromString(base64Credentials)).split(":")[0];
            cartService.save(memberEmail, cart.getId());

            return ResponseEntity.status(HttpStatus.CREATED).body(new ResultResponse(SuccessCode.CREATE_CART, cart));
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResultResponse(SuccessCode.NO_CONTENT_MEMBER, authorization));
    }

    @DeleteMapping("/cart/{itemId}")
    public ResponseEntity<ResultResponse> deleteCart(@RequestHeader(value = "Authorization") String authorization, @PathVariable Long itemId) {
        if (authorization != null && authorization.startsWith("Basic ")) {
            String base64Credentials = authorization.substring("Basic ".length());
            String memberEmail = new String(Base64Utils.decodeFromString(base64Credentials)).split(":")[0];
            cartService.delete(memberEmail, itemId);
            return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse(SuccessCode.DELETE_CART, authorization));
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResultResponse(SuccessCode.NO_CONTENT_MEMBER, authorization));
    }
}
