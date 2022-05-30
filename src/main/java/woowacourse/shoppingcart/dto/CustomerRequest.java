package woowacourse.shoppingcart.dto;

public class CustomerRequest {

    private String userName;
    private String password;

    private CustomerRequest() {
    }

    public CustomerRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
