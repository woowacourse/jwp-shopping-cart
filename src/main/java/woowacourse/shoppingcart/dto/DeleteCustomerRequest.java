package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotBlank;

public class DeleteCustomerRequest {

    private static final String INVALID_PASSWORD = "[ERROR] 비밀번호는 공백 또는 빈 값일 수 없습니다.";

    @NotBlank(message = INVALID_PASSWORD)
    private String password;

    public DeleteCustomerRequest() {
    }

    public DeleteCustomerRequest(final String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
