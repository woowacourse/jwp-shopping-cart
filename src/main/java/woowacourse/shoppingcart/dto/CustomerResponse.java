package woowacourse.shoppingcart.dto;

public class CustomerResponse {

    private String userName;

    private CustomerResponse() {
    }

    public CustomerResponse(final String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
