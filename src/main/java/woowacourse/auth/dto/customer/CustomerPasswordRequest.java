package woowacourse.auth.dto.customer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CustomerPasswordRequest {

	@NotBlank
	private String password;
	@NotBlank
	private String newPassword;
}
