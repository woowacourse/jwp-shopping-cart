package cart.presentation;

import cart.business.CartService;
import cart.business.MemberService;
import cart.business.ProductCRUDService;
import cart.business.domain.cart.CartItem;
import cart.business.domain.member.Member;
import cart.business.domain.member.MemberEmail;
import cart.business.domain.member.MemberPassword;
import cart.business.domain.product.Product;
import cart.presentation.dto.AuthInfo;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/carts")
public class CartController {

    private final CartService cartService;
    private final ProductCRUDService productCRUDService;
    private final MemberService memberService;

    public CartController(CartService cartService, ProductCRUDService productCRUDService,
                          MemberService memberService) {
        this.cartService = cartService;
        this.productCRUDService = productCRUDService;
        this.memberService = memberService;
    }

    @PostMapping
    public void cartCreate(HttpServletRequest request, @RequestBody ProductIdDto productIdDto) {
        Integer memberId = getMemberId(request);
        cartService.addCartItem(new CartItem(null, productIdDto.getId(), memberId));
        // TODO: URI CREATED 반환
    }

    @GetMapping
    public ResponseEntity<List<CartItemDto>> cartRead(HttpServletRequest request) {
        Integer memberId = getMemberId(request);

        List<CartItemDto> response = cartService.readAllCartItem(memberId).stream()
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

    private Integer getMemberId(HttpServletRequest request) {
        AuthInfo authInfo = BasicAuthorizationExtractor.extract(request);
        String email = authInfo.getEmail();
        String password = authInfo.getPassword();
        Member member = new Member(null, new MemberEmail(email), new MemberPassword(password));

        return memberService.findAndReturnId(member);
    }

    @DeleteMapping
    public void cartDelete(HttpServletRequest request, @RequestBody CartItemIdDto cartItemIdDto) {
        Integer memberId = getMemberId(request);
        memberService.validateExists(memberId);
        cartService.removeCartItem(cartItemIdDto.getId());
    }
}
