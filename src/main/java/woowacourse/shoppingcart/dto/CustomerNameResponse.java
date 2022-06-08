package woowacourse.shoppingcart.dto;

public class CustomerNameResponse {

    private final String name;

    public CustomerNameResponse(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
