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
import woowacourse.shoppingcart.application.dto.CustomerResponse;
import woowacourse.shoppingcart.application.dto.EmailDuplicationResponse;
import woowacourse.shoppingcart.application.dto.UserNameDuplicationResponse;
import woowacourse.shoppingcart.ui.dto.CustomerRequest;
import woowacourse.shoppingcart.ui.dto.EmailDuplicationRequest;
import woowacourse.shoppingcart.ui.dto.FindCustomerRequest;
import woowacourse.shoppingcart.ui.dto.UpdateCustomerRequest;
import woowacourse.shoppingcart.ui.dto.UserNameDuplicationRequest;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> createCustomer(@RequestBody CustomerRequest request) {
        Long createdId = service.createCustomer(request);
        return ResponseEntity.created(URI.create("/api/customers/" + createdId)).build();
    }

    @GetMapping("/me")
    public ResponseEntity<CustomerResponse> showCustomer(
        @AuthenticationPrincipal FindCustomerRequest findCustomerRequest) {
        return ResponseEntity.ok(service.findCustomer(findCustomerRequest));
    }

    @PutMapping("/me")
    public ResponseEntity<Void> updateCustomer(
        @AuthenticationPrincipal FindCustomerRequest findCustomerRequest,
        @RequestBody UpdateCustomerRequest updateCustomerRequest) {
        service.updateCustomer(findCustomerRequest, updateCustomerRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteCustomer(@AuthenticationPrincipal FindCustomerRequest findCustomerRequest) {
        service.deleteCustomer(findCustomerRequest);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/duplication/username")
    public ResponseEntity<UserNameDuplicationResponse> isUserNameDuplicated(
        @RequestBody UserNameDuplicationRequest request) {
        return ResponseEntity.ok(service.isUserNameDuplicated(request.getUsername()));
    }

    @PostMapping("/duplication/email")
    public ResponseEntity<EmailDuplicationResponse> isEmailDuplicated(@RequestBody EmailDuplicationRequest request) {
        return ResponseEntity.ok(service.isEmailDuplicated(request.getEmail()));
    }
}
