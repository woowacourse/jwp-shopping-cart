package woowacourse.shoppingcart.dto.request;

import javax.validation.constraints.NotBlank;

public class CheckDuplicationRequest {

    @NotBlank(message = "이름을 입력해주세요😉")
    private String userName;

    public CheckDuplicationRequest() {
    }

    public CheckDuplicationRequest(final String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
