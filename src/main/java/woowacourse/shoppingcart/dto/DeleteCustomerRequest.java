package woowacourse.shoppingcart.dto;

public class DeleteCustomerRequest {

    private String password;

    private DeleteCustomerRequest() {
    }

    public DeleteCustomerRequest(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
