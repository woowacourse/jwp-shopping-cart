package woowacourse.member.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class DuplicateEmailRequest {

    @NotBlank(message = "이메일은 빈 값일 수 없습니다.")
    @Email(message = "올바르지 않은 형식의 이메일입니다.")
    private String email;

    private DuplicateEmailRequest(){}

    public DuplicateEmailRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
