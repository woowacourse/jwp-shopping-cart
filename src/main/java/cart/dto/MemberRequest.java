package cart.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@RequiredArgsConstructor
public class MemberRequest {
    @NotBlank(message = "[ERROR] 이메일을 입력해주세요.")
    @Email(message = "[ERROR] 이메일 형식으로 입력해주세요.")
    @Length(message = "[ERROR] 이메일은 30자까지 입력가능합니다.", max = 30)
    private final String email;

    @NotNull(message = "[ERROR] 비밀번호를 입력해주세요.")
    @Length(message = "[ERROR] 비밀번호는 5자 이상, 30자이하까지 입력가능합니다.", max = 30, min = 5)
    private final String password;
}
