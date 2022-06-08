package woowacourse.shoppingcart.ui;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.dto.EmailAuthentication;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.OrderService;
import woowacourse.shoppingcart.application.dto.EmailDto;
import woowacourse.shoppingcart.application.dto.OrderDto;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.dto.request.OrderRequest;
import woowacourse.shoppingcart.dto.response.OrdersResponse;

@Validated
@RestController
@RequestMapping("/api/customers/{customerName}/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> addOrder(@AuthenticationPrincipal final EmailAuthentication emailAuthentication,
                                         @RequestBody @Valid final List<OrderRequest> orderDetails) {
        final Long orderId = orderService.addOrder(orderDetails.stream()
                .map(OrderDto::from)
                .collect(Collectors.toList()), EmailDto.from(emailAuthentication));
        return ResponseEntity.created(
                URI.create("/api/" + emailAuthentication.getEmail() + "/orders/" + orderId)).build();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrdersResponse> findOrder(@AuthenticationPrincipal EmailAuthentication emailAuthentication,
                                                    @PathVariable final Long orderId) {
        final OrdersResponse order = orderService.findOrderById(EmailDto.from(emailAuthentication), orderId);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<OrdersResponse>> findOrders(@AuthenticationPrincipal
                                                                       EmailAuthentication emailAuthentication) {
        final List<OrdersResponse> orders = orderService.findOrdersByCustomerName(EmailDto.from(emailAuthentication));
        return ResponseEntity.ok(orders);
    }
}
