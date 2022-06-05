package woowacourse.shoppingcart.controller;

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
import woowacourse.shoppingcart.service.CustomerService;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.ChangeGeneralInfoRequest;
import woowacourse.shoppingcart.dto.ChangePasswordRequest;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.DeleteCustomerRequest;

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
    public ResponseEntity<CustomerResponse> showCustomer(
            @AuthenticationPrincipal Customer customer) {
        return ResponseEntity.ok(new CustomerResponse(customer.getEmail(), customer.getUsername()));
    }

    @PatchMapping(value = "/me", params = "target=password")
    public ResponseEntity<Void> changePassword(@AuthenticationPrincipal Customer customer,
            @RequestBody ChangePasswordRequest changePasswordRequest) {
        customerService.changePassword(customer, changePasswordRequest.getOldPassword(),
                changePasswordRequest.getNewPassword());
        return ResponseEntity.ok().location(URI.create("/login")).build();
    }

    @PatchMapping(value = "/me", params = "target=generalInfo")
    public ResponseEntity<CustomerResponse> changeGeneral(
            @AuthenticationPrincipal Customer customer,
            @RequestBody ChangeGeneralInfoRequest changeGeneralInfoRequest) {
        final CustomerResponse customerResponse = customerService
                .changeGeneralInfo(customer, changeGeneralInfoRequest.getUsername());
        return ResponseEntity.ok().body(customerResponse);
    }

    @DeleteMapping("/me")
    public ResponseEntity<CustomerResponse> delete(@AuthenticationPrincipal Customer customer,
            @RequestBody DeleteCustomerRequest deleteCustomerRequest) {
        customerService.delete(customer, deleteCustomerRequest.getPassword());
        return ResponseEntity.noContent().location(URI.create("/")).build();
    }
}
