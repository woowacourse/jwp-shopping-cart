package woowacourse.auth.ui;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import woowacourse.auth.application.CustomerService;
import woowacourse.auth.domain.Customer;
import woowacourse.auth.dto.customer.CustomerDeleteRequest;
import woowacourse.auth.dto.customer.CustomerProfileRequest;
import woowacourse.auth.dto.customer.CustomerRequest;
import woowacourse.auth.dto.customer.CustomerResponse;
import woowacourse.auth.dto.customer.CustomerPasswordRequest;
import woowacourse.auth.dto.customer.CustomerUpdateResponse;
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
	public ResponseEntity<Void> signOut(@Login Customer customer,
		@RequestBody @Valid CustomerDeleteRequest request) {
		customerService.delete(customer, request);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/password")
	public ResponseEntity<Void> updatePassword(@Login Customer customer,
		@RequestBody @Valid CustomerPasswordRequest request) {
		customerService.updatePassword(customer, request);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/profile")
	public ResponseEntity<CustomerUpdateResponse> updateProfile(@Login Customer customer,
		@RequestBody @Valid CustomerProfileRequest request) {
		Customer updatedCustomer = customerService.updateProfile(customer, request);
		return ResponseEntity.ok(new CustomerUpdateResponse(updatedCustomer.getNickname()));
	}

	@GetMapping
	public ResponseEntity<CustomerResponse> find(@Login Customer customer) {
		return ResponseEntity.ok(new CustomerResponse(customer));
	}
}
