package woowacourse.shoppingcart.ui;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.dto.OrderRequest;

@RestController
@RequestMapping("/auth/customer/orders")
public class OrdersController {

    public OrdersController() {
    }

    @PostMapping
    public ResponseEntity<Void> order(
            @AuthenticationPrincipal final TokenRequest tokenRequest,
            @RequestBody final List<OrderRequest> productIdRequests) {
        return ResponseEntity.created(URI.create("/orders/" + tokenRequest.getCustomerId())).build();
    }
}
