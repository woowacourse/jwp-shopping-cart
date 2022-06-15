package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import woowacourse.auth.domain.Customer;
import woowacourse.auth.support.Login;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.application.OrderService;
import woowacourse.shoppingcart.dto.OrderResponse;
import woowacourse.shoppingcart.dto.ProductIdsRequest;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Validated
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> addOrder(@Login Customer customer, @RequestBody ProductIdsRequest request) {
        Orders order = orderService.order(customer.getId(), request.getProductIds());
        return ResponseEntity.created(makeUri(order.getId()))
            .body(OrderResponse.from(order));
    }

    private URI makeUri(Long id) {
        return ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{orderId}")
            .buildAndExpand(id)
            .toUri();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(@Login Customer customer, @PathVariable Long orderId) {
        Orders order = orderService.findOne(orderId, customer.getId());
        return ResponseEntity.ok(OrderResponse.from(order));
    }
}
