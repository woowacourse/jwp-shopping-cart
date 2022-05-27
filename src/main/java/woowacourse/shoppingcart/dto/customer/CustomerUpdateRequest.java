package woowacourse.shoppingcart.dto.customer;

public class CustomerUpdateRequest {

    private String username;

    private CustomerUpdateRequest() {
    }

    public CustomerUpdateRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
