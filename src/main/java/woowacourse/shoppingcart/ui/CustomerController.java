package woowacourse.shoppingcart.ui;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.application.dto.CustomerDto;
import woowacourse.shoppingcart.dto.CustomerRequest;

@Controller
@RequestMapping("/api")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/customers")
    public ResponseEntity<Void> createCustomers(@RequestBody final CustomerRequest request) {
        final Long customerId = customerService.createCustomer(CustomerDto.fromCustomerRequest(request));
        return ResponseEntity.created(URI.create("/api/customers/" + customerId)).build();
    }
}
