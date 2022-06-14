package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.customer.CustomerRequest;
import woowacourse.shoppingcart.dto.customer.CustomerResponse;
import woowacourse.shoppingcart.dto.customer.EmailRequest;
import woowacourse.shoppingcart.dto.customer.EmailResponse;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<CustomerResponse> findCustomer(@AuthenticationPrincipal Long customerId) {
        CustomerResponse customerResponse = customerService.findCustomerBy(customerId);
        return ResponseEntity.ok().body(customerResponse);
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> registerCustomers(@RequestBody final CustomerRequest customerRequest) {
        CustomerResponse customerResponse = customerService.register(customerRequest);
        return ResponseEntity.created(URI.create("/auth/login")).body(customerResponse);
    }

    @PostMapping("/email/validate")
    public ResponseEntity<EmailResponse> checkValidEmail(@RequestBody @Valid EmailRequest emailRequest) {
        return ResponseEntity.ok().body(customerService.checkValidEmail(emailRequest));
    }

    @PutMapping
    public ResponseEntity<Void> updateCustomer(@AuthenticationPrincipal Long customerId, @RequestBody CustomerRequest customerRequest) {
        customerService.edit(customerId, customerRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCustomer(@AuthenticationPrincipal Long customerId) {
        customerService.delete(customerId);
        return ResponseEntity.noContent().build();
    }
}
