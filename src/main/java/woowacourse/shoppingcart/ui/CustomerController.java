package woowacourse.shoppingcart.ui;

import java.net.URI;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.request.CustomerRequest;
import woowacourse.shoppingcart.dto.request.PasswordRequest;
import woowacourse.shoppingcart.dto.response.CustomerResponse;
import woowacourse.shoppingcart.dto.response.UserNameDuplicationResponse;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Void> addCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
        customerService.addCustomer(customerRequest);
        return ResponseEntity.created(URI.create("/customers/me")).build();
    }

    @GetMapping("/username/uniqueness")
    public ResponseEntity<UserNameDuplicationResponse> checkDuplicationUserName(
            @RequestParam String username) {
        return ResponseEntity.ok(customerService.checkDuplication(username));
    }

    @GetMapping("/me")
    public ResponseEntity<CustomerResponse> findMyInfo(@AuthenticationPrincipal String username) {
        return ResponseEntity.ok(customerService.getCustomer(username));
    }

    @PutMapping("/me/password")
    public ResponseEntity<Void> updateMyPassword(@AuthenticationPrincipal String username,
                                                 @RequestBody PasswordRequest passwordRequest) {
        customerService.updatePassword(username, passwordRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/me")
    public ResponseEntity<Void> updateInfo(@AuthenticationPrincipal String username,
                                           @RequestBody CustomerRequest customerRequest) {
        customerService.updateInfo(username, customerRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteCustomer(@AuthenticationPrincipal String username) {
        customerService.deleteCustomer(username);
        return ResponseEntity.noContent().build();
    }
}
