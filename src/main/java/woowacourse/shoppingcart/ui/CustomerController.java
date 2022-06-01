package woowacourse.shoppingcart.ui;

import java.net.URI;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.dto.CustomerResponse;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.auth.ui.LoginCustomer;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.ui.dto.CustomerSignUpRequest;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Void> saveCustomer(@RequestBody @Valid CustomerSignUpRequest customerSignUpRequest) {
        customerService.save(customerSignUpRequest.toServiceRequest());
        return ResponseEntity.created(URI.create("/api/customers")).build();
    }

    @GetMapping("/me")
    public ResponseEntity<CustomerResponse> showCustomer(@AuthenticationPrincipal @Valid LoginCustomer loginCustomer) {
        CustomerResponse customerResponse = customerService.findById(loginCustomer.getId());
        return ResponseEntity.ok(customerResponse);
    }
}
