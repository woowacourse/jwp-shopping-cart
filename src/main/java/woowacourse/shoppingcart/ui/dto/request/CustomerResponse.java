package woowacourse.shoppingcart.ui.dto.request;

public class CustomerResponse {
    private final String name;
    private final String email;

    public CustomerResponse(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
