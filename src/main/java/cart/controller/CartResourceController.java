package cart.controller;

import cart.auth.AuthorizationExtractor;
import cart.auth.BasicAuthorizationExtractor;
import cart.config.AuthenticationPrincipal;
import cart.domain.cart.Item;
import cart.domain.member.Member;
import cart.dto.response.ProductResponse;
import cart.service.CartService;
import cart.service.MemberService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public List<ProductResponse> findAllProducts(@AuthenticationPrincipal final Member member) {
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

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createItem(@RequestBody final Map<String, Long> requestMap,
                           @AuthenticationPrincipal final Member member) {
        final long memberId = memberService.findMemberId(member);
        final long productId = requestMap.get("productId");
        final Item item = new Item(memberId, productId);
        cartService.insert(item);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteItem(@PathVariable final long id, @AuthenticationPrincipal final Member member) {
        cartService.delete(id);
    }
}
