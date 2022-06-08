package woowacourse.shoppingcart.ui;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.OrderService;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.dto.OrderRequest;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/members/me/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addOrder(@AuthenticationPrincipal long memberId,
                         @RequestBody @Valid final List<OrderRequest> orderDetails) {
        orderService.addOrder(orderDetails, memberId);
    }

    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public Orders findOrder(@AuthenticationPrincipal long memberId,
                            @PathVariable final Long orderId) {
        return orderService.findOrderById(memberId, orderId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Orders> findOrders(@AuthenticationPrincipal long memberId) {
        return orderService.findOrdersByMemberId(memberId);
    }
}
