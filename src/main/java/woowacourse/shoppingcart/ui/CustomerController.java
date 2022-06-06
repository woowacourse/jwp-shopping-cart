package woowacourse.shoppingcart.ui;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.MemberOnly;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/email")
    @ResponseStatus(HttpStatus.OK)
    public void checkEmailExistence(@RequestParam String email) {
        customerService.validateEmailDuplication(email);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse save(@RequestBody @Valid CustomerRequest customerRequest) {
        return customerService.save(customerRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponse showCustomer(@MemberOnly Long customerId) {
        return customerService.find(customerId);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateCustomer(@MemberOnly Long customerId,
                               @RequestBody @Valid CustomerRequest customerRequest) {
        customerService.update(customerId, customerRequest);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@MemberOnly Long customerId) {
        customerService.delete(customerId);
    }
}
