package woowacourse.shoppingcart.dto;

public class CustomerUpdateRequest {

    private String userName;
    private String password;
    private String newPassword;

    public CustomerUpdateRequest() {
    }

    public CustomerUpdateRequest(final String userName, final String password, final String newPassword) {
        this.userName = userName;
        this.password = password;
        this.newPassword = newPassword;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
