package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotBlank;

public class DeleteCustomerRequest {
    @NotBlank(message = "[ERROR] 비밀번호는 공백 또는 빈 값일 수 없습니다.")
    private String password;


    public DeleteCustomerRequest() {
    }

    public DeleteCustomerRequest(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
