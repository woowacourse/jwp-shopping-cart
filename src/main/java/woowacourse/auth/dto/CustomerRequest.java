package woowacourse.auth.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import woowacourse.auth.domain.Customer;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CustomerRequest {

	@Email
	private String email;
	@NotBlank
	private String password;

	public Customer toEntity() {
		return new Customer(email, password);
	}
}

