package woowacourse.shoppingcart.ui;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.CustomerRequest;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@Valid @RequestBody CustomerRequest customerRequest) {
        Customer customer = customerService.save(customerRequest);
        return ResponseEntity.created(URI.create("/api/customers/" + customer.getId())).build();
    }
}
