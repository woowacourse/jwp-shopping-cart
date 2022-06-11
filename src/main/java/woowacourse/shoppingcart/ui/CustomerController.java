package woowacourse.shoppingcart.ui;

import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.request.CustomerRequest;
import woowacourse.shoppingcart.dto.response.CustomerResponse;
import woowacourse.shoppingcart.dto.request.CustomerUpdateRequest;
import woowacourse.shoppingcart.dto.request.PasswordRequest;

@RestController
@RequestMapping
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody CustomerRequest customerRequest) {
        customerService.create(customerRequest);
        URI uri = URI.create("/signin");
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/customers")
    public CustomerResponse findCustomer(@AuthenticationPrincipal Long customerId) {
        return customerService.findById(customerId);
    }

    @PutMapping("/customers")
    public void modify(@AuthenticationPrincipal Long customerId,
            @RequestBody CustomerUpdateRequest customerUpdateRequest) {
        customerService.update(customerId, customerUpdateRequest);
    }

    @DeleteMapping("/customers")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal Long customerId,
            @RequestBody PasswordRequest passwordRequest) {
        customerService.delete(customerId, passwordRequest);
    }
}
