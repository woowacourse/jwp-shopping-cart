package cart.controller;

import cart.dto.cart.RequestCartDto;
import cart.dto.cart.ResponseCartDto;
import cart.service.CartService;
import cart.util.BasicAuth;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartRestController {

    private final CartService cartService;

    public CartRestController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/carts")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@BasicAuth final Long memberId, @RequestBody @Valid final RequestCartDto requestCartDto) {
        cartService.create(memberId, requestCartDto);
    }

    @GetMapping("/carts")
    @ResponseStatus(HttpStatus.OK)
    public List<ResponseCartDto> display(@BasicAuth final Long memberId) {
        return cartService.display(memberId);
    }
}
