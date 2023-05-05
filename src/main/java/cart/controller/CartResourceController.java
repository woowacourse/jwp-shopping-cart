package cart.controller;

import cart.auth.AuthenticationPrincipal;
import cart.dto.application.MemberDto;
import cart.dto.request.MemberRequest;
import cart.dto.response.ItemEntityResponse;
import cart.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cart/items")
public class CartResourceController {

    private final CartService cartService;

    public CartResourceController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public List<ItemEntityResponse> findAllProducts(@AuthenticationPrincipal @Valid final MemberRequest memberRequest) {
        final MemberDto member = new MemberDto(memberRequest);

        return cartService.findAll(member).stream()
                .map(ItemEntityResponse::new)
                .collect(Collectors.toList());
    }

    @PostMapping("/{productId}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createItem(@PathVariable final long productId,
                           @AuthenticationPrincipal final MemberRequest memberRequest) {
        final MemberDto member = new MemberDto(memberRequest);

        cartService.insert(member, productId);
    }

    @DeleteMapping("/{itemId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteItem(@PathVariable final long itemId,
                           @AuthenticationPrincipal @Valid final MemberRequest memberRequest) {
        final MemberDto member = new MemberDto(memberRequest);

        cartService.delete(itemId, member);
    }
}
