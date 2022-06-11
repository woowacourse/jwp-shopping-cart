package woowacourse.shoppingcart.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.auth.support.LoginCustomer;
import woowacourse.shoppingcart.dto.OrdersRequestDto;
import woowacourse.shoppingcart.dto.OrdersResponseDto;
import woowacourse.shoppingcart.service.OrdersService;

@RestController
@RequestMapping("/api/customers/{customerId}/orders")
public class OrdersController {

    private final OrdersService ordersService;
    private final AuthService authService;

    public OrdersController(final OrdersService ordersService, final AuthService authService) {
        this.ordersService = ordersService;
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<Void> ordersRequest(@PathVariable final Long customerId,
                                              @RequestBody OrdersRequestDto ordersRequestDto,
                                              @AuthenticationPrincipal LoginCustomer loginCustomer) {
        authService.checkAuthorization(customerId, loginCustomer.getEmail());

        final Long ordersId = ordersService.order(ordersRequestDto.getProductIds(), customerId);
        return ResponseEntity.created(URI.create("/api/customers/" + customerId + "/orders/" + ordersId)).build();
    }

    @GetMapping
    public ResponseEntity<List<OrdersResponseDto>> getCustomerOrders(
            @PathVariable final Long customerId,
            @AuthenticationPrincipal LoginCustomer loginCustomer)
    {
        authService.checkAuthorization(customerId, loginCustomer.getEmail());

        final List<OrdersResponseDto> ordersResponseDtos = ordersService.findCustomerOrders(customerId);
        return ResponseEntity.ok(ordersResponseDtos);
    }

    @GetMapping("/{ordersId}")
    public ResponseEntity<OrdersResponseDto> getOrders(@PathVariable final Long customerId,
                                                       @PathVariable final Long ordersId,
                                                       @AuthenticationPrincipal LoginCustomer loginCustomer) {
        authService.checkAuthorization(customerId, loginCustomer.getEmail());

        final OrdersResponseDto ordersResponseDto = ordersService.findOrders(ordersId);
        return ResponseEntity.ok(ordersResponseDto);
    }
}
