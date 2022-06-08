package woowacourse.shoppingcart.customer.application.dto.request;

public class CustomerPasswordUpdateRequest {

    private String password;
    private String newPassword;

    public CustomerPasswordUpdateRequest() {
    }

    public CustomerPasswordUpdateRequest(final String password, final String newPassword) {
        this.password = password;
        this.newPassword = newPassword;
    }

    public String getPassword() {
        return password;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
