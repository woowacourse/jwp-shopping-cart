package cart.controller.api;

import cart.auth.annotation.Authorization;
import cart.dto.request.CertifiedCustomer;
import cart.dto.response.CartProductResponseDto;
import cart.dtomapper.CartProductResponseDtoMapper;
import cart.entity.cart.CartEntity;
import cart.entity.product.ProductEntity;
import cart.service.CartService;
import java.net.URI;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public final class CartApiController {

    private final CartService cartService;

    public CartApiController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping(value = "/items", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CartProductResponseDto>> showCart(@Authorization CertifiedCustomer certifiedCustomer) {
        final List<ProductEntity> products = cartService.findAllProductsByCustomerId(certifiedCustomer.getId());
        return ResponseEntity.ok().body(CartProductResponseDtoMapper.asList(products));
    }

    @PostMapping("/{productId}")
    public ResponseEntity<Void> addItem(
        @PathVariable(name = "productId") Long productId,
        @Authorization CertifiedCustomer certifiedCustomer
    ) {
        final Long savedCartId = cartService.save(new CartEntity(certifiedCustomer.getId(), productId));
        return ResponseEntity.created(URI.create("/cart/" + savedCartId)).build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteItem(
        @PathVariable(name = "productId") Long productId,
        @Authorization CertifiedCustomer certifiedCustomer
    ) {
        final Long cartId = cartService.findFirstCartIdBy(certifiedCustomer.getId(), productId);
        cartService.delete(cartId);
        return ResponseEntity.noContent().build();
    }
}
