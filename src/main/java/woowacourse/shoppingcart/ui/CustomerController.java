package woowacourse.shoppingcart.ui;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.CustomerRegisterRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> registerCustomer(
            @RequestBody CustomerRegisterRequest customerRegisterRequest) {
        final Long customerId = customerService.registerCustomer(customerRegisterRequest);
        final CustomerResponse customerResponse = customerService.findById(customerId);

        return ResponseEntity.created(URI.create("/customers/" + customerId)).body(customerResponse);
    }
}
