package woowacourse.shoppingcart.dto;

public class CustomerPasswordUpdateRequest {

    private String oldPassword;
    private String newPassword;

    public CustomerPasswordUpdateRequest() {
    }

    public CustomerPasswordUpdateRequest(final String oldPassword, final String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public CustomerPasswordUpdateServiceRequest toServiceRequest(final Long id) {
        return new CustomerPasswordUpdateServiceRequest(id, oldPassword, newPassword);
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
