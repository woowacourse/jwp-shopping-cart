package woowacourse.shoppingcart.ui;

import java.net.URI;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.config.auth.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.customer.CustomerCreateRequest;
import woowacourse.shoppingcart.dto.customer.CustomerDeleteRequest;
import woowacourse.shoppingcart.dto.customer.CustomerResponse;
import woowacourse.shoppingcart.dto.customer.CustomerUpdateRequest;
import woowacourse.shoppingcart.exception.ForbiddenAccessException;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody CustomerCreateRequest request) {
        Long savedId = customerService.save(request);
        return ResponseEntity.created(URI.create("/api/customers/" + savedId)).build();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponse findCustomer(@PathVariable long id,
                                         @AuthenticationPrincipal Customer customer) {
        validateAuthorizedUser(customer, id);
        return CustomerResponse.from(customer);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponse update(@PathVariable long id,
                                   @Valid @RequestBody CustomerUpdateRequest request,
                                   @AuthenticationPrincipal Customer customer) {
        validateAuthorizedUser(customer, id);
        customerService.update(id, request);
        return customerService.findByIdForUpdateView(id);
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id,
                       @RequestBody CustomerDeleteRequest request,
                       @AuthenticationPrincipal Customer customer) {
        validateAuthorizedUser(customer, id);
        customerService.delete(id, request.getPassword());
    }

    private void validateAuthorizedUser(Customer customer, long id) {
        if (!customer.getId().equals(id)) {
            throw new ForbiddenAccessException();
        }
    }
}
