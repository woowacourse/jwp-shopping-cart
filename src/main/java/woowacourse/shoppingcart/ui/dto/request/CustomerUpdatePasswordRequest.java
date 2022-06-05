package woowacourse.shoppingcart.ui.dto.request;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import woowacourse.shoppingcart.service.dto.CustomerUpdatePasswordServiceRequest;

public class CustomerUpdatePasswordRequest {

    @NotBlank
    @Length(min = 8)
    private String oldPassword;

    @NotBlank
    @Length(min = 8)
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

    public CustomerUpdatePasswordServiceRequest toServiceRequest() {
        return new CustomerUpdatePasswordServiceRequest(this.oldPassword, this.newPassword);
    }
}
