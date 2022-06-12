package woowacourse.shoppingcart.ui.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserNameDuplicationRequest {

    @NotEmpty(message = "이름은 비어있을 수 없습니다.")
    @Size(min = 5, max = 20)
    private final String username;

    private UserNameDuplicationRequest() {
        this(null);
    }

    public UserNameDuplicationRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
