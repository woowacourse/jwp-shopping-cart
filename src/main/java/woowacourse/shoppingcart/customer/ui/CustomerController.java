package woowacourse.shoppingcart.customer.ui;

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
import woowacourse.shoppingcart.customer.application.CustomerService;
import woowacourse.shoppingcart.customer.application.dto.ChangePasswordDto;
import woowacourse.shoppingcart.customer.application.dto.RegisterDto;
import woowacourse.shoppingcart.customer.ui.dto.ChangeGeneralInfoRequest;
import woowacourse.shoppingcart.customer.ui.dto.ChangePasswordRequest;
import woowacourse.shoppingcart.customer.ui.dto.CustomerRequest;
import woowacourse.shoppingcart.customer.ui.dto.CustomerResponse;
import woowacourse.shoppingcart.customer.ui.dto.DeleteCustomerRequest;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Void> register(@Valid @RequestBody CustomerRequest customerRequest) {
        customerService.register(RegisterDto.from(customerRequest));
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
        customerService
                .changePassword(ChangePasswordDto.from(changePasswordRequest, email));
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
