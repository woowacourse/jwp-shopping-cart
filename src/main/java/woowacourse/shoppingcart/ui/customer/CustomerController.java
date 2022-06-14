package woowacourse.shoppingcart.ui.customer;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.application.dto.CustomerDetailServiceResponse;
import woowacourse.shoppingcart.ui.customer.dto.request.CustomerDeleteRequest;
import woowacourse.shoppingcart.ui.customer.dto.request.CustomerPasswordUpdateRequest;
import woowacourse.shoppingcart.ui.customer.dto.request.CustomerProfileUpdateRequest;
import woowacourse.shoppingcart.ui.customer.dto.request.CustomerRegisterRequest;
import woowacourse.shoppingcart.ui.customer.dto.response.CustomerDetailResponse;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid final CustomerRegisterRequest request) {
        customerService.save(request.toServiceDto());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<CustomerDetailResponse> findMyDetail(@AuthenticationPrincipal final Long id) {
        final CustomerDetailServiceResponse serviceResponse = customerService.findById(id);
        final CustomerDetailResponse customerDetailResponse = CustomerDetailResponse.from(serviceResponse);
        return ResponseEntity.ok(customerDetailResponse);
    }

    @PutMapping("/profile")
    public ResponseEntity<Void> updateProfile(@AuthenticationPrincipal final Long id,
                                              @RequestBody @Valid final CustomerProfileUpdateRequest request) {
        customerService.updateName(request.toServiceRequest(id));
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/password")
    public ResponseEntity<Void> updatePassword(@AuthenticationPrincipal final Long id,
                                               @RequestBody @Valid final CustomerPasswordUpdateRequest request) {
        customerService.updatePassword(request.toServiceRequest(id));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@AuthenticationPrincipal final Long id,
                                       @RequestBody @Valid final CustomerDeleteRequest request) {
        customerService.delete(request.toServiceRequest(id));
        return ResponseEntity.noContent().build();
    }
}
