package woowacourse.shoppingcart.controller;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.shoppingcart.dto.request.CustomerRequest;
import woowacourse.shoppingcart.dto.response.CustomerResponse;
import woowacourse.shoppingcart.dto.response.EmailDuplicationResponse;
import woowacourse.shoppingcart.service.CustomerService;

@RestController
@RequestMapping("/api")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/customers")
    public ResponseEntity<Void> createCustomer(@RequestBody CustomerRequest customerRequest) {
        long id = customerService.create(customerRequest);
        return ResponseEntity.created(URI.create("/api/customers/" + id)).build();
    }

    @GetMapping("/customers/{customerId}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable int customerId) {
        CustomerResponse customerResponse = customerService.getCustomerById(customerId);
        return ResponseEntity.ok(customerResponse);
    }

    @GetMapping("/validation")
    public ResponseEntity<EmailDuplicationResponse> checkEmailDuplication(@RequestParam String email) {
        EmailDuplicationResponse response = customerService.isDuplicatedEmail(email);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/customers/{customerId}")
    public ResponseEntity<Void> updateCustomer(@PathVariable int customerId,
                                               @RequestBody CustomerRequest customerRequest) {
        customerService.updateCustomerById(customerId, customerRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/customers/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable int customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.noContent().build();
    }
}
