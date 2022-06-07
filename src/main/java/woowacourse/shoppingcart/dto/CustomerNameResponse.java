package woowacourse.shoppingcart.dto;

public class CustomerNameResponse {

    private String name;

    private CustomerNameResponse() {

    }

    public CustomerNameResponse(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
