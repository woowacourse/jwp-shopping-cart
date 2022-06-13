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
import woowacourse.shoppingcart.dto.customer.LoginCustomer;
import woowacourse.shoppingcart.dto.customer.request.CustomerSaveRequest;
import woowacourse.shoppingcart.dto.customer.request.CustomerUpdateRequest;
import woowacourse.shoppingcart.dto.customer.request.EmailDuplicateRequest;
import woowacourse.shoppingcart.dto.customer.request.UsernameDuplicateRequest;
import woowacourse.shoppingcart.dto.customer.response.CustomerResponse;
import woowacourse.shoppingcart.dto.customer.response.EmailDuplicateResponse;
import woowacourse.shoppingcart.dto.customer.response.UsernameDuplicateResponse;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody CustomerSaveRequest request) {
        CustomerResponse response = customerService.save(request);
        return ResponseEntity.created(URI.create("/api/customers/" + response.getId())).build();
    }

    @GetMapping("/me")
    public ResponseEntity<CustomerResponse> findCustomer(@AuthenticationPrincipal LoginCustomer loginCustomer) {
        CustomerResponse customerResponse = customerService.find(loginCustomer);
        return ResponseEntity.ok(customerResponse);
    }

    @PutMapping("/me")
    public ResponseEntity<Void> updateCustomer(@AuthenticationPrincipal LoginCustomer loginCustomer,
            @Valid @RequestBody CustomerUpdateRequest updateRequest) {
        customerService.update(loginCustomer, updateRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("me")
    public ResponseEntity<Void> deleteCustomer(@AuthenticationPrincipal LoginCustomer loginCustomer) {
        customerService.delete(loginCustomer);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("duplication/username")
    public ResponseEntity<UsernameDuplicateResponse> checkDuplicateUsername(
            @Valid @RequestBody UsernameDuplicateRequest request) {
        UsernameDuplicateResponse usernameDuplicateResponse = customerService.checkUsernameDuplicate(request);
        return ResponseEntity.ok(usernameDuplicateResponse);
    }

    @PostMapping("duplication/email")
    public ResponseEntity<EmailDuplicateResponse> checkDuplicateEmail(
            @Valid @RequestBody EmailDuplicateRequest request) {
        EmailDuplicateResponse emailDuplicateResponse = customerService.checkEmailDuplicate(request);
        return ResponseEntity.ok(emailDuplicateResponse);
    }
}
