package woowacourse.shoppingcart.ui.dto;

import woowacourse.shoppingcart.application.dto.CustomerUpdatePasswordRequest;

import javax.validation.constraints.NotBlank;

public class CustomerChangePasswordRequest {

    @NotBlank(message = "[ERROR] 이전 비밀번호는 공백일 수 없습니다.")
    private String prevPassword;
    @NotBlank(message = "[ERROR] 새로운 비밀번호는 공백일 수 없습니다.")
    private String newPassword;

    public CustomerChangePasswordRequest() {
    }

    public CustomerChangePasswordRequest(final String prevPassword, final String newPassword) {
        this.prevPassword = prevPassword;
        this.newPassword = newPassword;
    }

    public CustomerUpdatePasswordRequest toServiceRequest() {
        return new CustomerUpdatePasswordRequest(prevPassword, newPassword);
    }

    public String getPrevPassword() {
        return prevPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
