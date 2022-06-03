package woowacourse.shoppingcart.service.dto;

public class CustomerDeleteServiceRequest {
    private String password;

    public CustomerDeleteServiceRequest() {
    }

    public CustomerDeleteServiceRequest(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
