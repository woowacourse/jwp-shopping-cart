package woowacourse.shoppingcart.ui;

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
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.CustomerNameResponse;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/email/validate")
    @ResponseStatus(HttpStatus.OK)
    public void checkEmail(@RequestParam String email) {
        customerService.checkDuplicationEmail(email);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse save(@RequestBody CustomerRequest customerRequest) {
        return customerService.save(customerRequest);
    }

    @GetMapping("/me/name")
    @ResponseStatus(HttpStatus.OK)
    public CustomerNameResponse showCustomerName(@AuthenticationPrincipal Long customerId) {
        return customerService.findNameById(customerId);
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponse showCustomer(@AuthenticationPrincipal Long customerId) {
        return customerService.findById(customerId);
    }

    @PutMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public void updateCustomer(@AuthenticationPrincipal Long customerId, @RequestBody CustomerRequest customerRequest) {
        customerService.update(customerId, customerRequest);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@AuthenticationPrincipal Long customerId) {
        customerService.delete(customerId);
    }
}
