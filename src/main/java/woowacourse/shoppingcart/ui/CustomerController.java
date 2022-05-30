package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.CustomerDto;
import woowacourse.shoppingcart.dto.SignupRequest;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody @Valid SignupRequest signupRequest) throws URISyntaxException {
        customerService.createCustomer(signupRequest);

        return ResponseEntity.created(new URI("/signin"))
                .build();
    }

    @GetMapping("/customers")
    public CustomerDto getCustomers(@AuthenticationPrincipal long customerId) {
        return customerService.getById(customerId);
    }
}
