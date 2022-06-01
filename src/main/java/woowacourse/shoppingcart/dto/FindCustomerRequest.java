package woowacourse.shoppingcart.dto;

public class FindCustomerRequest {

    private final String name;

    public FindCustomerRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
