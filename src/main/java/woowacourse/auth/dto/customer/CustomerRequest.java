package woowacourse.auth.dto.customer;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CustomerRequest {

	@NotBlank
	private String email;
	@NotBlank
	private String password;
	@NotBlank
	private String nickname;
}

