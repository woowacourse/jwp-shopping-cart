package woowacourse.shoppingcart.dto;

public class CustomerResponse {

    private String email;
    private String userName;

    public CustomerResponse(final String email, final String userName) {
        this.email = email;
        this.userName = userName;
    }

    public CustomerResponse() {
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }
}
