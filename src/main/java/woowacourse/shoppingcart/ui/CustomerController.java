package woowacourse.shoppingcart.ui;

import java.net.URI;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.customer.ChangeGeneralInfoRequest;
import woowacourse.shoppingcart.dto.customer.ChangePasswordRequest;
import woowacourse.shoppingcart.dto.customer.CustomerRequest;
import woowacourse.shoppingcart.dto.customer.CustomerResponse;
import woowacourse.shoppingcart.dto.customer.DeleteCustomerRequest;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Void> register(@Valid @RequestBody CustomerRequest customerRequest) {
        customerService.register(customerRequest.getEmail(),
                customerRequest.getPassword(),
                customerRequest.getUsername());
        return ResponseEntity.created(URI.create("/login")).build();
    }

    @GetMapping("/me")
    public ResponseEntity<CustomerResponse> showCustomer(@AuthenticationPrincipal String email) {
        CustomerResponse customerResponse = customerService.showCustomer(email);
        return ResponseEntity.ok(customerResponse);
    }

    @PatchMapping(value = "/me", params = "target=password")
    public ResponseEntity<Void> changePassword(@AuthenticationPrincipal String email, @RequestBody
            ChangePasswordRequest changePasswordRequest) {
        customerService.changePassword(email, changePasswordRequest.getOldPassword(),
                changePasswordRequest.getNewPassword());
        return ResponseEntity.ok().location(URI.create("/login")).build();
    }

    @PatchMapping(value = "/me", params = "target=generalInfo")
    public ResponseEntity<CustomerResponse> changeGeneral(@AuthenticationPrincipal String email, @RequestBody
            ChangeGeneralInfoRequest changeGeneralInfoRequest) {
        final CustomerResponse customerResponse = customerService
                .changeGeneralInfo(email, changeGeneralInfoRequest.getUsername());
        return ResponseEntity.ok().body(customerResponse);
    }

    @DeleteMapping("/me")
    public ResponseEntity<CustomerResponse> delete(@AuthenticationPrincipal String email, @RequestBody
            DeleteCustomerRequest deleteCustomerRequest) {
        customerService.delete(email, deleteCustomerRequest.getPassword());
        return ResponseEntity.noContent().location(URI.create("/")).build();
    }
}
