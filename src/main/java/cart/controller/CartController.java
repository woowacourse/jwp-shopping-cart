package cart.controller;

import cart.authority.Authority;
import cart.controller.dto.request.CartItemCreationRequest;
import cart.controller.dto.request.CartItemDeleteRequest;
import cart.controller.dto.request.MemberIdRequest;
import cart.controller.dto.response.CartItemResponse;
import cart.domain.dto.CartDto;
import cart.domain.dto.ProductDto;
import cart.service.CartService;
import cart.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cart/items")
public class CartController {

    private final CartService cartService;
    private final ProductService productService;

    public CartController(final CartService cartService, final ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
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
        final List<ProductDto> productDtos = productService.findById(cartDtos);

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

    @DeleteMapping
    public ResponseEntity<String> deleteProduct(
            @RequestBody @Valid final CartItemDeleteRequest productId,
            @Authority final MemberIdRequest memberId
    ) {
        cartService.delete(memberId, productId);
        return ResponseEntity.noContent().build();
    }
}
