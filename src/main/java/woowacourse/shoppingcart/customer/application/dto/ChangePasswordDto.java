package woowacourse.shoppingcart.customer.application.dto;

import woowacourse.shoppingcart.customer.ui.dto.ChangePasswordRequest;

public class ChangePasswordDto {
    private final String email;
    private final String oldPassword;
    private final String newPassword;

    private ChangePasswordDto(String email, String oldPassword, String newPassword) {
        this.email = email;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public static ChangePasswordDto from(ChangePasswordRequest changePasswordRequest, String email) {
        return new ChangePasswordDto(email, changePasswordRequest.getOldPassword(),
                changePasswordRequest.getNewPassword());
    }

    public String getEmail() {
        return email;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
