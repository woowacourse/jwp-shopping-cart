package woowacourse.shoppingcart.ui;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.domain.LoginMemberPrincipal;
import woowacourse.auth.ui.dto.LoginMember;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.service.CustomerService;
import woowacourse.shoppingcart.ui.dto.request.CustomerDeleteRequest;
import woowacourse.shoppingcart.ui.dto.request.CustomerRequest;
import woowacourse.shoppingcart.ui.dto.request.CustomerUpdatePasswordRequest;
import woowacourse.shoppingcart.ui.dto.request.CustomerUpdateProfileRequest;
import woowacourse.shoppingcart.ui.dto.response.CustomerResponse;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid CustomerRequest customerRequest) {
        customerService.create(customerRequest.toServiceRequest());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<CustomerResponse> findById(@LoginMemberPrincipal LoginMember loginMember) {
        final Customer customer = customerService.getById(loginMember.getId());
        return ResponseEntity.ok().body(toCustomerResponse(customer));
    }

    @PutMapping("/profile")
    public ResponseEntity<Void> updateProfile(@LoginMemberPrincipal LoginMember loginMember,
                                              @RequestBody @Valid CustomerUpdateProfileRequest customerUpdateProfileRequest) {
        customerService.updateProfile(loginMember.getId(), customerUpdateProfileRequest.toServiceRequest());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/password")
    public ResponseEntity<Void> updatePassword(@LoginMemberPrincipal LoginMember loginMember,
                                               @RequestBody @Valid CustomerUpdatePasswordRequest customerUpdatePasswordRequest) {
        customerService.updatePassword(loginMember.getId(), customerUpdatePasswordRequest.toServiceRequest());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@LoginMemberPrincipal LoginMember loginMember,
                                       @RequestBody @Valid CustomerDeleteRequest customerDeleteRequest) {
        customerService.delete(loginMember.getId(), customerDeleteRequest.toServiceRequest());
        return ResponseEntity.noContent().build();
    }

    private CustomerResponse toCustomerResponse(Customer customer) {
        return new CustomerResponse(customer.getEmail(), customer.getName());
    }
}
