package woowacourse.member.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class MemberPasswordUpdateRequest {

    private static final String REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@!?-])[A-Za-z\\d@!?-]{6,20}";

    @NotBlank(message = "비밀번호에는 공백이 허용되지 않습니다.")
    @Pattern(regexp = REGEX, message = "올바른 비밀번호 형식으로 입력해주세요.")
    private String oldPassword;

    @NotBlank(message = "비밀번호에는 공백이 허용되지 않습니다.")
    @Pattern(regexp = REGEX, message = "올바른 비밀번호 형식으로 입력해주세요.")
    private String newPassword;

    private MemberPasswordUpdateRequest() {
    }

    public MemberPasswordUpdateRequest(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
