package woowacourse.member.dto;

import javax.validation.constraints.NotBlank;

public class WithdrawalRequest {

    @NotBlank(message = "비밀번호는 빈 값일 수 없습니다.")
    private String password;

    private WithdrawalRequest() {
    }

    public WithdrawalRequest(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
