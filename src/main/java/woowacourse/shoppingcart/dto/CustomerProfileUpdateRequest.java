package woowacourse.shoppingcart.dto;

public class CustomerProfileUpdateRequest {

    private String name;

    public CustomerProfileUpdateRequest() {
    }

    public CustomerProfileUpdateRequest(final String name) {
        this.name = name;
    }

    public CustomerProfileUpdateServiceRequest toServiceRequest(final Long id) {
        return new CustomerProfileUpdateServiceRequest(id, name);
    }

    public String getName() {
        return name;
    }
}
