package woowacourse.shoppingcart.customer.application.dto.request;

public class CustomerRemoveRequest {

    private String password;

    public CustomerRemoveRequest() {
    }

    public CustomerRemoveRequest(final String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
