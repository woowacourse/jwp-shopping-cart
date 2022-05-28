package woowacourse.auth.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import woowacourse.auth.domain.Member;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MemberRequest {

	@Email
	private String email;
	@NotBlank
	private String password;

	public Member toEntity() {
		return new Member(email, password);
	}
}

