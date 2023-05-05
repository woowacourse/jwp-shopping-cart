package cart.controller;

import cart.authority.Authority;
import cart.controller.dto.request.CartItemCreationRequest;
import cart.controller.dto.request.MemberIdRequest;
import cart.controller.dto.response.CartItemResponse;
import cart.domain.dto.CartDto;
import cart.domain.dto.ProductDto;
import cart.service.CartService;
import cart.service.product.ProductReadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cart/items")
public class CartController {

    private final CartService cartService;
    private final ProductReadService productReadService;

    public CartController(final CartService cartService, final ProductReadService productReadService) {
        this.cartService = cartService;
        this.productReadService = productReadService;
    }

    @PostMapping
    public ResponseEntity<String> addProduct(
            @RequestBody @Valid final CartItemCreationRequest productId,
            @Authority final MemberIdRequest memberId
    ) {
        cartService.addProduct(productId, memberId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> findIdMemberId(@Authority final MemberIdRequest memberId) {
        final List<CartDto> cartDtos = cartService.getProducts(memberId);
        final List<ProductDto> productDtos = productReadService.findById(cartDtos);

        List<CartItemResponse> cartItemResponses = productDtos.stream()
                .map(productDto -> new CartItemResponse(
                        productDto.getId(),
                        productDto.getName(),
                        productDto.getImage(),
                        productDto.getPrice())
                )
                .collect(Collectors.toList());

        return ResponseEntity.ok(cartItemResponses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(
            @PathVariable("id") Long productId,
            @Authority final MemberIdRequest memberId
    ) {
        cartService.delete(memberId, productId);
        return ResponseEntity.noContent().build();
    }
}
