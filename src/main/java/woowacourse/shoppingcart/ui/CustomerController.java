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
import org.springframework.web.bind.annotation.RestController;

import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.PasswordRequest;
import woowacourse.shoppingcart.dto.UserNameDuplicationRequest;
import woowacourse.shoppingcart.dto.UserNameDuplicationResponse;

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

    @GetMapping("/username/duplication")
    public ResponseEntity<UserNameDuplicationResponse> checkDuplicationUserName(
            @RequestBody UserNameDuplicationRequest userNameDuplicationRequest) {
        return ResponseEntity.ok(customerService.checkDuplication(userNameDuplicationRequest));
    }

    @GetMapping("/me")
    public ResponseEntity<CustomerResponse> findMyInfo(@AuthenticationPrincipal Long id) {
        return ResponseEntity.ok(customerService.getCustomer(id));
    }

    @PutMapping("/me/password")
    public ResponseEntity<Void> updateMyPassword(@AuthenticationPrincipal Long id,
                                                 @RequestBody PasswordRequest passwordRequest) {
        customerService.updatePassword(id, passwordRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/me")
    public ResponseEntity<Void> updateInfo(@AuthenticationPrincipal Long id,
                                           @RequestBody CustomerRequest customerRequest) {
        customerService.updateInfo(id, customerRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteCustomer(@AuthenticationPrincipal Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
