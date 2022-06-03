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
        customerService.create(customerRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<CustomerResponse> findByToken(@LoginMemberPrincipal LoginMember loginMember) {
        final CustomerResponse customerResponse = customerService.findById(loginMember.getId());
        return ResponseEntity.ok().body(customerResponse);
    }

    @PutMapping("/profile")
    public ResponseEntity<Void> updateProfile(@LoginMemberPrincipal LoginMember loginMember,
                                              @RequestBody @Valid CustomerUpdateProfileRequest customerUpdateProfileRequest) {
        customerService.updateProfile(loginMember.getId(), customerUpdateProfileRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/password")
    public ResponseEntity<Void> updatePassword(@LoginMemberPrincipal LoginMember loginMember,
                                               @RequestBody @Valid CustomerUpdatePasswordRequest customerUpdatePasswordRequest) {
        customerService.updatePassword(loginMember.getId(), customerUpdatePasswordRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@LoginMemberPrincipal LoginMember loginMember,
                                       @RequestBody @Valid CustomerDeleteRequest customerDeleteRequest) {
        customerService.delete(loginMember.getId(), customerDeleteRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
