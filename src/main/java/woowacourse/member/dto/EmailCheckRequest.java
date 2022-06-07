package woowacourse.member.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class EmailCheckRequest {

    @NotBlank(message = "이메일에는 공백이 허용되지 않습니다.")
    @Email(message = "올바른 이메일 형식으로 입력해주세요.")
    private String email;

    private EmailCheckRequest() {
    }

    public EmailCheckRequest(final String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
