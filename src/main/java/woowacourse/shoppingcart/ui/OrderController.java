package woowacourse.shoppingcart.ui;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.dto.TokenRequest;

@RestController
public class OrderController {

    @PostMapping("/auth/customer/orders")
    public ResponseEntity<Void> order(final @AuthenticationPrincipal TokenRequest tokenRequest,
                                      final @RequestBody List<OrderRequest> orderRequests) {
        return ResponseEntity.created(URI.create("/orders/")).build();
    }
}
