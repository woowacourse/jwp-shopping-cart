package cart.controller.api;

import cart.annotation.Authenticate;
import cart.controller.dto.request.MemberRequest;
import cart.controller.dto.request.ProductIdRequest;
import cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/cart")
public class CartApiController {

    private final CartService cartService;

    public CartApiController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/product")
    public ResponseEntity<Void> save(
            final Long memberId,
            @RequestBody @Valid final ProductIdRequest productIdRequest
    ) {
        cartService.save(memberId, productIdRequest);
        return ResponseEntity.ok().build();
    }



}
