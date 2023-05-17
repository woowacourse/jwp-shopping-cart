package cart.service.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class MemberUpdateRequest {
	@NotBlank(message = "회원 이름은 공백일 수 없습니다.")
	@Length(min = 1, max = 8, message = "회원님의 이름을 1글자 이상 8글자 이하고 작성해주세요.")
	private String name;

	@NotNull(message = "가입 이메일은 공백일 수 없습니다.")
	@Email
	private String email;

	@NotNull(message = "비밀번호는 공백일 수 없습니다.")
	@Length(min = 4, max = 16, message = "비밀번호를 4글자 이상 16글자 미만으로 설정해주세요.")
	private String password;

	public MemberUpdateRequest() {
	}

	public MemberUpdateRequest(final String name, final String email, final String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
}
