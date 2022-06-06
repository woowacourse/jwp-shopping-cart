package woowacourse.shoppingcart.ui;

import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.DeleteCustomerRequest;
import woowacourse.shoppingcart.dto.UpdateCustomerRequest;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public CustomerResponse getCustomers(@AuthenticationPrincipal long customerId) {
        return customerService.getById(customerId);
    }

    @PutMapping
    public void updateCustomer(@AuthenticationPrincipal long customerId,
                               @RequestBody @Valid UpdateCustomerRequest updateCustomerRequest) {
        customerService.update(customerId, updateCustomerRequest);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCustomer(@AuthenticationPrincipal long customerId,
                                               @RequestBody DeleteCustomerRequest deleteCustomerRequest) {
        customerService.delete(customerId, deleteCustomerRequest);
        return ResponseEntity.noContent().build();
    }
}
