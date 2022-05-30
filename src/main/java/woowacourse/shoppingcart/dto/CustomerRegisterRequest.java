package woowacourse.shoppingcart.dto;

public class CustomerRegisterRequest {

    private String email;
    private String userName;
    private String password;

    public CustomerRegisterRequest() {
    }

    public CustomerRegisterRequest(final String email, final String userName, final String password) {
        this.email = email;
        this.userName = userName;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
