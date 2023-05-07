package cart.presentation.controller;

import cart.business.domain.member.Member;
import cart.business.service.CartService;
import cart.business.service.MemberService;
import cart.business.service.ProductService;
import cart.config.ResolvedMember;
import cart.presentation.adapter.CartItemConverter;
import cart.presentation.dto.CartItemDto;
import cart.presentation.dto.CartItemIdDto;
import cart.presentation.dto.ProductIdDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/carts")
public class CartController {

    private final CartService cartService;
    private final ProductService productService;
    private final MemberService memberService;

    public CartController(CartService cartService, ProductService productService,
                          MemberService memberService) {
        this.cartService = cartService;
        this.productService = productService;
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<Void> cartCreate(@ResolvedMember Member member,
                                           @Valid @RequestBody ProductIdDto productIdDto) {
        Integer memberId = memberService.findAndReturnId(member);
        cartService.addCartItem(CartItemConverter.toEntity(productIdDto.getId(), memberId));

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<CartItemDto>> cartRead(@ResolvedMember Member member) {
        Integer memberId = memberService.findAndReturnId(member);
        List<CartItemDto> response = cartService.readAllCartItem(memberId).stream()
                .map(cartItem -> CartItemConverter.toDto(
                        productService.readById(cartItem.getProductId()),
                        cartItem.getId())
                ).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<Void> cartDelete(@ResolvedMember Member member,
                                           @Valid @RequestBody CartItemIdDto cartItemIdDto) {
        Integer memberId = memberService.findAndReturnId(member);
        memberService.validateExists(memberId);
        cartService.removeCartItem(cartItemIdDto.getId());

        return ResponseEntity.noContent().build();
    }
}
