package woowacourse.shoppingcart.dto.customer;

public class CustomerDeleteRequest {

    private String password;

    public CustomerDeleteRequest(String password) {
        this.password = password;
    }

    private CustomerDeleteRequest() {
    }

    public String getPassword() {
        return password;
    }
}
