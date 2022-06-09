package woowacourse.shoppingcart.dto.request;

import javax.validation.constraints.NotBlank;

public class CustomerUserNameRequest {

    @NotBlank(message = "유저 이름은 빈칸일 수 없습니다.")
    private String userName;

    private CustomerUserNameRequest() {
    }

    public CustomerUserNameRequest(final String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
