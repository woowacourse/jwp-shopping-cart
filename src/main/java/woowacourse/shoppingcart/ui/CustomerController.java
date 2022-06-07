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
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.request.CustomerDeleteRequest;
import woowacourse.shoppingcart.dto.request.CustomerRequest;
import woowacourse.shoppingcart.dto.response.CustomerResponse;
import woowacourse.shoppingcart.dto.request.CustomerUpdatePasswordRequest;
import woowacourse.shoppingcart.dto.request.CustomerUpdateProfileRequest;

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
    public ResponseEntity<CustomerResponse> findByToken(@AuthenticationPrincipal Long id) {
        final CustomerResponse customerResponse = customerService.findById(id);
        return ResponseEntity.ok(customerResponse);
    }

    @PutMapping("/profile")
    public ResponseEntity<Void> updateProfile(@AuthenticationPrincipal Long id,
                                              @RequestBody @Valid CustomerUpdateProfileRequest customerUpdateProfileRequest) {
        customerService.updateProfile(id, customerUpdateProfileRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/password")
    public ResponseEntity<Void> updatePassword(@AuthenticationPrincipal Long id,
                                               @RequestBody @Valid CustomerUpdatePasswordRequest customerUpdatePasswordRequest) {
        customerService.updatePassword(id, customerUpdatePasswordRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@AuthenticationPrincipal Long id,
                                       @RequestBody @Valid CustomerDeleteRequest customerDeleteRequest) {
        customerService.delete(id, customerDeleteRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
