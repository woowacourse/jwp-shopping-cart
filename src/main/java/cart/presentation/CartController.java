package cart.presentation;

import cart.business.CartService;
import cart.business.ProductCRUDService;
import cart.business.domain.product.Product;
import cart.presentation.dto.CartItemDto;
import cart.presentation.dto.CartItemIdDto;
import cart.presentation.dto.ProductIdDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/cart")
public class CartController {

    private final CartService cartService;
    private final ProductCRUDService productCRUDService;

    public CartController(CartService cartService, ProductCRUDService productCRUDService) {
        this.cartService = cartService;
        this.productCRUDService = productCRUDService;
    }

    @PostMapping
    public void cartCreate(@RequestBody ProductIdDto productIdDto) {
        cartService.addCartItem(productIdDto.getId(), 0);
        // TODO: URI CREATED 반환
    }

    @GetMapping
    public ResponseEntity<List<CartItemDto>> cartRead() {
        List<CartItemDto> response = cartService.readAllCartItem(0).stream()
                .map(cartItem -> toCartItemDto(
                        productCRUDService.readById(cartItem.getProductId()),
                        cartItem.getId())
                ).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    private CartItemDto toCartItemDto(Product product, Integer cartItemId) {
        return new CartItemDto(cartItemId, product.getName(),
                product.getUrl(), product.getPrice());
    }

    @DeleteMapping
    public void cartDelete(@RequestBody CartItemIdDto cartItemIdDto) {
        cartService.removeCartItem(0, cartItemIdDto.getId());
    }
}
