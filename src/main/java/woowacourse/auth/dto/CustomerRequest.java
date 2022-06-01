package woowacourse.auth.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{4,20}",
	message = "비밀번호 형식이 올바르지 않습니다.")
	private String password;
	@Size(min = 2, max = 10, message = "닉네임은 2~10 길이어야 합니다.")
	private String nickname;

	public Customer toEntity(String password) {
		return new Customer(email, password, nickname);
	}
}

