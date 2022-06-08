package woowacourse.shoppingcart.ui;

import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.dto.OrderRequest;

@Validated
@RestController
@RequestMapping("/auth/customer/orders")
public class OrderController {

    @PostMapping
    public ResponseEntity<Void> createOrder(@AuthenticationPrincipal TokenRequest tokenRequest,
                                            @RequestBody @Valid final List<OrderRequest> orderRequests) {
        return ResponseEntity.created(
                URI.create("/orders/" + tokenRequest.getId())).build();
    }
}
