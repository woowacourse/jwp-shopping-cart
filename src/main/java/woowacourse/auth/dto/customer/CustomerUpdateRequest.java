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
public class CustomerUpdateRequest {

	@Size(min = 2, max = 10, message = "닉네임은 2~10 길이어야 합니다.")
	private String nickname;
	@NotBlank
	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{4,20}",
		message = "비밀번호 형식이 올바르지 않습니다.")
	private String password;
	@NotBlank
	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{4,20}",
		message = "비밀번호 형식이 올바르지 않습니다.")
	private String newPassword;
}
