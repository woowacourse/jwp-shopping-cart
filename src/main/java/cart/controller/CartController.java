package cart.controller;

import cart.argumnetresolver.Login;
import cart.dto.CartItemResponse;
import cart.dto.CartRequest;
import cart.dto.CartResponse;
import cart.service.CartService;
import cart.service.ProductService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/carts")
public class CartController {

  private final CartService cartService;
  private final ProductService productService;

  public CartController(final CartService cartService, final ProductService productService) {
    this.cartService = cartService;
    this.productService = productService;
  }

  @GetMapping
  public ResponseEntity<List<CartItemResponse>> findCartItems(@Login final long memberId) {
    final List<CartResponse> cartsByMemberId = cartService.findCartByMemberId(memberId);
    return ResponseEntity.ok(productService.findById(cartsByMemberId));
  }

  @PostMapping
  public ResponseEntity<String> addCart(@Login final long memberId, @Valid @RequestBody final CartRequest cartRequest) {
    cartService.addCart(memberId, cartRequest);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCart(@PathVariable(value = "id") final long productId, @Login final long memberId) {
    cartService.deleteByMemberIdAndProductId(memberId, productId);
    return ResponseEntity.ok().build();
  }
}
