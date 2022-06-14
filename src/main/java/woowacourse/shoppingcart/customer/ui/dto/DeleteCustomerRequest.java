package woowacourse.shoppingcart.customer.ui.dto;

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
