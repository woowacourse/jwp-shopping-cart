package woowacourse.shoppingcart.dto.request;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class CustomerUpdatePasswordRequest {

    @NotBlank
    @Length(min = 8, max = 15)
    private String oldPassword;
    @NotBlank
    @Length(min = 8, max = 15)
    private String newPassword;

    public CustomerUpdatePasswordRequest() {
    }

    public CustomerUpdatePasswordRequest(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
