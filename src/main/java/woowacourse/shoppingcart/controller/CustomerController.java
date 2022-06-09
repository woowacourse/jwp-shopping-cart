package woowacourse.shoppingcart.controller;

import java.net.URI;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.auth.support.LoginCustomer;
import woowacourse.shoppingcart.dto.CustomerDto;
import woowacourse.shoppingcart.dto.DeleteCustomerDto;
import woowacourse.shoppingcart.dto.SignUpDto;
import woowacourse.shoppingcart.dto.UpdateCustomerDto;
import woowacourse.shoppingcart.exception.ForbiddenException;
import woowacourse.shoppingcart.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Void> signUp(@Valid @RequestBody final SignUpDto signUpDto) {
        final Long createdId = customerService.signUp(signUpDto);

        return ResponseEntity.created(URI.create("/api/customers/" + createdId)).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> detailCustomer(@PathVariable final Long id,
                                                      @AuthenticationPrincipal final LoginCustomer loginCustomer) {
        checkAuthorization(id, loginCustomer.getEmail());

        final CustomerDto customer = customerService.findCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable final Long id,
                                                      @RequestBody final UpdateCustomerDto updateCustomerDto,
                                                      @AuthenticationPrincipal final LoginCustomer loginCustomer) {
        checkAuthorization(id, loginCustomer.getEmail());

        final CustomerDto customerDto = customerService.updateCustomer(id, updateCustomerDto);
        return ResponseEntity.ok(customerDto);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable final Long id,
                                               @RequestBody final DeleteCustomerDto deleteCustomerDto,
                                               @AuthenticationPrincipal final LoginCustomer loginCustomer) {
        checkAuthorization(id, loginCustomer.getEmail());

        customerService.deleteCustomer(id, deleteCustomerDto);
        return ResponseEntity.noContent().build();
    }

    private void checkAuthorization(final Long id, final String email) {
        final CustomerDto loginCustomer = customerService.findCustomerByEmail(email);
        if (!loginCustomer.getId().equals(id)) {
            throw new ForbiddenException();
        }
    }
}
