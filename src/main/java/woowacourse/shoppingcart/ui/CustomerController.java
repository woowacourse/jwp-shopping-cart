package woowacourse.shoppingcart.ui;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.customer.CustomerCreateRequest;
import woowacourse.shoppingcart.dto.customer.CustomerResponse;
import woowacourse.shoppingcart.dto.customer.CustomerUpdateRequest;
import woowacourse.shoppingcart.exception.ForbiddenAccessException;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody CustomerCreateRequest request) {
        Long savedId = customerService.save(request);
        return ResponseEntity.created(URI.create("/api/customers/" + savedId)).build();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponse findCustomer(@PathVariable long id, @AuthenticationPrincipal Customer customer) {
        validateAuthorizedUser(id, customer);
        return new CustomerResponse(customer);
    }

    private void validateAuthorizedUser(long id, Customer customer) {
        if (!customer.getId().equals(id)) {
            throw new ForbiddenAccessException();
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponse update(@PathVariable long id, @Valid @RequestBody CustomerUpdateRequest request,
        @AuthenticationPrincipal Customer customer) {
        validateAuthorizedUser(id, customer);
        customerService.update(id, request);
        Customer updatedCustomer = customerService.findById(id);

        return new CustomerResponse(updatedCustomer);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id, @AuthenticationPrincipal Customer customer) {
        validateAuthorizedUser(id, customer);
        customerService.delete(id);
    }
}
