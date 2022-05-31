package woowacourse.shoppingcart.dto;

public class CustomerUpdateResponse {

    private String userName;

    public CustomerUpdateResponse() {
    }

    public CustomerUpdateResponse(final String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
