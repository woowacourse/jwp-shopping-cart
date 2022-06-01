package woowacourse.shoppingcart.dto;

public class CustomerDeleteRequest {

    private String password;

    public CustomerDeleteRequest() {
    }

    public CustomerDeleteRequest(final String password) {
        this.password = password;
    }

    public CustomerDeleteServiceRequest toServiceRequest(final Long id) {
        return new CustomerDeleteServiceRequest(id, password);
    }

    public String getPassword() {
        return password;
    }
}
