package woowacourse.shoppingcart.ui;

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
import woowacourse.shoppingcart.dto.SignupRequest;
import woowacourse.shoppingcart.dto.UpdateCustomerRequest;

@RequestMapping("/api/customers")
@RestController
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@Valid @RequestBody final SignupRequest signupRequest) {
        customerService.save(signupRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateInfo(@RequestBody final UpdateCustomerRequest updateCustomerRequest,
        HttpServletRequest request) {
        customerService.updateInfo((String)request.getAttribute("username"), updateCustomerRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> updatePassword(@RequestBody final UpdateCustomerRequest updateCustomerRequest,
        HttpServletRequest request) {
        customerService.updatePassword((String)request.getAttribute("username"), updateCustomerRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCustomer(final HttpServletRequest request) {
        customerService.deleteByUsername((String)request.getAttribute("username"));
        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }
}
