package woowacourse.shoppingcart.dto;

public class CustomerDetailResponse {

    private String name;
    private String email;

    private CustomerDetailResponse() {
    }

    public CustomerDetailResponse(final String name, final String email) {
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
