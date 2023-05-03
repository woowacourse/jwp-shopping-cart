package cart.controller;

import cart.auth.AuthorizationExtractor;
import cart.auth.BasicAuthorizationExtractor;
import cart.auth.exception.AuthorizationException;
import cart.domain.member.Member;
import cart.dto.response.ProductResponse;
import cart.service.CartService;
import cart.service.MemberService;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart/items")
public class CartResourceController {

    private final CartService cartService;
    private final AuthorizationExtractor<Member> extractor = new BasicAuthorizationExtractor();
    private final MemberService memberService;

    public CartResourceController(final CartService cartService, final MemberService memberService) {
        this.cartService = cartService;
        this.memberService = memberService;
    }

    @GetMapping
    public List<ProductResponse> findAllProducts(final HttpServletRequest request) {
        final Member member = extractor.extract(request);

        if (!memberService.isMember(member)) {
            throw new AuthorizationException("사용자 정보가 없습니다.");
        }
        final long memberId = memberService.findMemberId(member);

        return cartService.findAll(memberId).stream()
                .map(productEntity -> new ProductResponse(
                        productEntity.getId(),
                        productEntity.getName(),
                        productEntity.getPrice(),
                        productEntity.getImageUrl())
                )
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteItem(@PathVariable final long id) {
        cartService.delete(id);
    }
}
