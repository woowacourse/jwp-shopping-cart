package cart.controller;

import cart.argumnetresolver.Login;
import cart.dto.CartRequest;
import cart.service.CartService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/carts")
public class CartController {

  private final CartService cartService;

  public CartController(CartService cartService) {
    this.cartService = cartService;
  }

  @PostMapping
  public ResponseEntity<String> addCart(@Login long memberId, @Valid @RequestBody CartRequest cartRequest) {
    cartService.addCart(memberId, cartRequest);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
