package woowacourse.shoppingcart.ui.dto.response;

public class CustomerResponse {
    private String email;
    private String name;

    public CustomerResponse() {
    }

    public CustomerResponse(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
