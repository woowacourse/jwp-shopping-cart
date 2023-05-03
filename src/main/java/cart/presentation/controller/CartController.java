package cart.presentation.controller;

import cart.business.domain.member.Member;
import cart.business.service.CartService;
import cart.business.service.MemberService;
import cart.business.service.ProductService;
import cart.config.ResolvedMember;
import cart.presentation.adapter.DomainConverter;
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
    public void cartCreate(@ResolvedMember Member member, @RequestBody ProductIdDto productIdDto) {
        Integer memberId = memberService.findAndReturnId(member);
        cartService.addCartItem(DomainConverter.toCartItemWithoutId(productIdDto.getId(), memberId));
        // TODO: URI CREATED 반환
    }

    @GetMapping
    public ResponseEntity<List<CartItemDto>> cartRead(@ResolvedMember Member member) {
        Integer memberId = memberService.findAndReturnId(member);
        List<CartItemDto> response = cartService.readAllCartItem(memberId).stream()
                .map(cartItem -> DomainConverter.toCartItemDto(
                        productService.readById(cartItem.getProductId()),
                        cartItem.getId())
                ).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public void cartDelete(@ResolvedMember Member member, @RequestBody CartItemIdDto cartItemIdDto) {
        Integer memberId = memberService.findAndReturnId(member);
        memberService.validateExists(memberId);
        cartService.removeCartItem(cartItemIdDto.getId());
    }
}
