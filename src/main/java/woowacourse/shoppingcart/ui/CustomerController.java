package woowacourse.shoppingcart.ui;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.SignupRequest;
import woowacourse.shoppingcart.dto.UpdateCustomerRequest;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@Valid @RequestBody SignupRequest signupRequest) {
        Customer customer = customerService.save(signupRequest);
        return ResponseEntity.created(URI.create("/api/customers/" + customer.getId())).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateInfo(@RequestBody UpdateCustomerRequest updateCustomerRequest,
        HttpServletRequest request) {
        customerService.updateInfo((String)request.getAttribute("username"), updateCustomerRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> updatePassword(@RequestBody UpdateCustomerRequest updateCustomerRequest,
        HttpServletRequest request) {
        customerService.updatePassword((String)request.getAttribute("username"), updateCustomerRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCustomer(HttpServletRequest request) {
        customerService.deleteByUsername((String)request.getAttribute("username"));
        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }
}
