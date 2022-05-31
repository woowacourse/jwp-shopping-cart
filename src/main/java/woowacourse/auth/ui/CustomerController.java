package woowacourse.auth.ui;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import woowacourse.auth.application.CustomerService;
import woowacourse.auth.domain.Customer;
import woowacourse.auth.dto.CustomerRequest;
import woowacourse.auth.dto.CustomerResponse;
import woowacourse.auth.dto.CustomerUpdateRequest;
import woowacourse.auth.dto.CustomerUpdateResponse;
import woowacourse.auth.support.Login;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

	private final CustomerService customerService;

	@PostMapping
	public ResponseEntity<CustomerResponse> signUp(@RequestBody @Valid CustomerRequest customerRequest) {
		Customer customer = customerService.signUp(customerRequest);
		URI uri = createUri(customer.getId());
		return ResponseEntity.created(uri).body(new CustomerResponse(customer));
	}

	private URI createUri(Long id) {
		return ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/" + id)
			.build().toUri();
	}

	@DeleteMapping
	public ResponseEntity<Void> signOut(@Login Customer customer) {
		customerService.delete(customer);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping
	public ResponseEntity<CustomerUpdateResponse> update(@Login Customer customer,
		@RequestBody @Valid CustomerUpdateRequest request) {
		Customer updatedCustomer = customerService.update(customer, request);
		return ResponseEntity.ok(new CustomerUpdateResponse(updatedCustomer.getNickname()));
	}
}
