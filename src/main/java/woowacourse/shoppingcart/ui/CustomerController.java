package woowacourse.shoppingcart.ui;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.CustomerUpdateRequest;

@RestController
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/users")
    public ResponseEntity<Void> addCustomer(@RequestBody @Valid CustomerRequest customerRequest) {
        customerService.registCustomer(customerRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/me")
    public ResponseEntity<CustomerResponse> getCustomer(@AuthenticationPrincipal String email) {
        Customer customer = customerService.findByEmail(email);
        CustomerResponse customerResponse = new CustomerResponse(customer.getEmail(), customer.getNickname());
        return ResponseEntity.ok(customerResponse);
    }

    @DeleteMapping("/users/me")
    public ResponseEntity<Void> deleteCustomer(@AuthenticationPrincipal String email) {
        customerService.deleteByEmail(email);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/users/me")
    public ResponseEntity<Void> updateCustomer(@AuthenticationPrincipal String email,
                                               @RequestBody @Valid CustomerUpdateRequest customerUpdateRequest) {
        customerService.updateCustomer(email, customerUpdateRequest);
        return ResponseEntity.noContent().build();
    }
}
