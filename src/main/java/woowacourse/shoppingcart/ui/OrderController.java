package woowacourse.shoppingcart.ui;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.domain.LoginMemberPrincipal;
import woowacourse.auth.ui.dto.LoginMember;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Order;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.service.OrderService;
import woowacourse.shoppingcart.ui.dto.request.OrderCreateRequest;
import woowacourse.shoppingcart.ui.dto.response.OrderDetailResponse;
import woowacourse.shoppingcart.ui.dto.response.OrderResponse;

@RequestMapping("/api/customer/orders")
@RestController
public class OrderController {
    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@LoginMemberPrincipal LoginMember loginMember,
                                       @RequestBody List<OrderCreateRequest> orderCreateRequests) {

        final long orderId = orderService.order(loginMember.getId(), orderCreateRequests);
        return ResponseEntity.created(URI.create("/api/customer/orders/" + orderId)).build();
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> findAll(@LoginMemberPrincipal LoginMember loginMember) {
        final List<Order> orders = orderService.findOrdersByCustomerId(loginMember.getId());

        return ResponseEntity.ok(toOrderResponses(orders));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> findById(@LoginMemberPrincipal LoginMember loginMember,
                                                  @PathVariable long orderId) {
        final Order order = orderService.findByOrderId(orderId);

        return ResponseEntity.ok(toOrderResponse(order));
    }

    private List<OrderResponse> toOrderResponses(final List<Order> orders) {
        return orders.stream()
                .map(this::toOrderResponse)
                .collect(Collectors.toList());
    }

    private OrderResponse toOrderResponse(final Order order) {
        final List<OrderDetailResponse> orderDetailResponses = order.getOrderDetails()
                .stream()
                .map(this::toOrderDetailResponse)
                .collect(Collectors.toList());

        return new OrderResponse(order.getTotalPrice(), order.getId(), orderDetailResponses);
    }

    private OrderDetailResponse toOrderDetailResponse(final OrderDetail orderDetail) {
        final Cart cart = orderDetail.getCart();
        final Product product = cart.getProduct();

        return new OrderDetailResponse(product.getId(), cart.getQuantity(), product.getPrice(), product.getName(),
                product.getImageUrl());
    }
}
