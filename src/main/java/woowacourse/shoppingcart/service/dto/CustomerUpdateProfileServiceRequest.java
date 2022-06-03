package woowacourse.shoppingcart.service.dto;

public class CustomerUpdateProfileServiceRequest {
    private String name;

    public CustomerUpdateProfileServiceRequest() {
    }

    public CustomerUpdateProfileServiceRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
