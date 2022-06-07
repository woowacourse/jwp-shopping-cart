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
import woowacourse.shoppingcart.application.OrderService;
import woowacourse.shoppingcart.dto.OrderRequest;

@Validated
@RestController
@RequestMapping("/auth/customer/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> addOrder(final @AuthenticationPrincipal TokenRequest request,
                                         @RequestBody @Valid final List<OrderRequest> orderDetails) {
        final Long customerId = orderService.addOrder(orderDetails, request.getCustomerId());
        return ResponseEntity.created(URI.create("/orders/" + customerId)).build();
    }
}
