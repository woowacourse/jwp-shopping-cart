package woowacourse.auth.ui;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import woowacourse.auth.domain.Customer;
import woowacourse.auth.dto.CustomerRequest;
import woowacourse.auth.dto.CustomerResponse;
import woowacourse.auth.service.CustomerService;

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
}
