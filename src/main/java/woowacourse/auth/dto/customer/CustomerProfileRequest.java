package woowacourse.auth.dto.customer;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CustomerProfileRequest {

	@NotBlank
	private String nickname;
}
