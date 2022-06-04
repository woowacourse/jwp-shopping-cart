package woowacourse.shoppingcart.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

public class UsernameDuplicationRequest {
    @NotBlank(message = "아이디를 입력해주세요.")
    @Length(min = 4, max = 20, message = "아이디는 4자 이상 20자 이하여야 합니다.")
    private String username;

    private UsernameDuplicationRequest() {
    }

    public UsernameDuplicationRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
