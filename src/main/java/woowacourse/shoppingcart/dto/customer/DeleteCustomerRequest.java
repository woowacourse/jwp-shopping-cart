package woowacourse.shoppingcart.dto.customer;

public class DeleteCustomerRequest {
    private String password;

    public DeleteCustomerRequest() {
    }

    public DeleteCustomerRequest(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
