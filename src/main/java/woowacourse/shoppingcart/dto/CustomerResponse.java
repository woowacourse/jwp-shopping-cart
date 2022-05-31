package woowacourse.shoppingcart.dto;

public class CustomerResponse {

    private String name;

    private CustomerResponse() {
    }

    public CustomerResponse(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
