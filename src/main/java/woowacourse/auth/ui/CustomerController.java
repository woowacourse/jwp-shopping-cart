package woowacourse.auth.ui;

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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.auth.application.CustomerService;
import woowacourse.auth.domain.Customer;
import woowacourse.auth.dto.customer.CustomerUpdateRequest;
import woowacourse.auth.dto.customer.CustomerUpdateResponse;
import woowacourse.auth.dto.customer.SignupRequest;
import woowacourse.auth.dto.customer.SignupResponse;
import woowacourse.auth.support.Login;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<SignupResponse> signUp(@RequestBody @Valid SignupRequest signupRequest) {
        Customer customer = customerService.signUp(signupRequest);
        URI uri = createUri(customer.getId());
        return ResponseEntity.created(uri).body(new SignupResponse(customer));
    }

    @DeleteMapping
    public ResponseEntity<Void> signOut(@Login Customer customer) {
        customerService.delete(customer);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping
    public ResponseEntity<CustomerUpdateResponse> update(@Login Customer customer,
                                                         @RequestBody @Valid CustomerUpdateRequest request) {
        Customer updatedCustomer = customerService.update(customer, request);
        return ResponseEntity.ok(new CustomerUpdateResponse(updatedCustomer.getNickname()));
    }

    @GetMapping
    public ResponseEntity<SignupResponse> find(@Login Customer customer) {
        return ResponseEntity.ok(new SignupResponse(customer));
    }

    private URI createUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + id)
                .build().toUri();
    }
}
