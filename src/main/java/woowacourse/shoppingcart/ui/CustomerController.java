package woowacourse.shoppingcart.ui;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.request.CustomerPasswordRequest;
import woowacourse.shoppingcart.dto.request.CustomerRequest;
import woowacourse.shoppingcart.dto.response.CustomerResponse;
import woowacourse.shoppingcart.dto.request.CustomerUpdateRequest;
import woowacourse.shoppingcart.dto.request.LoginCustomer;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> addCustomer(
        @RequestBody CustomerRequest customerRequest) {
        CustomerResponse customerResponse = customerService.addCustomer(customerRequest);
        return ResponseEntity.created(URI.create("/customers/me")).body(customerResponse);
    }

    @GetMapping("/me")
    public ResponseEntity<CustomerResponse> getMe(
        @AuthenticationPrincipal LoginCustomer loginCustomer) {
        CustomerResponse customerResponse = CustomerResponse.of(loginCustomer);
        return ResponseEntity.ok().body(customerResponse);
    }

    @PutMapping("/me")
    public ResponseEntity<CustomerResponse> updateMe(
        @AuthenticationPrincipal LoginCustomer loginCustomer,
        @RequestBody CustomerUpdateRequest customerUpdateRequest) {
        CustomerResponse customerResponse = customerService.updateCustomer(customerUpdateRequest, loginCustomer);
        return ResponseEntity.ok().body(customerResponse);
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMe(@AuthenticationPrincipal LoginCustomer loginCustomer,
        @RequestBody CustomerPasswordRequest customerPasswordRequest) {
        customerService.deleteCustomer(customerPasswordRequest, loginCustomer);
        return ResponseEntity.noContent().build();
    }
}
