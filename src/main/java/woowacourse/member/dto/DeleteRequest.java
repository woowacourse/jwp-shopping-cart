package woowacourse.member.dto;

import javax.validation.constraints.NotBlank;

public class DeleteRequest {

    @NotBlank(message = "비밀번호는 빈 값일 수 없습니다.")
    private String password;

    private DeleteRequest() {
    }

    public DeleteRequest(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
