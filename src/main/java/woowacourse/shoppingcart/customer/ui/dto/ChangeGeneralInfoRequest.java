package woowacourse.shoppingcart.customer.ui.dto;

public class ChangeGeneralInfoRequest {

    private String username;

    public ChangeGeneralInfoRequest() {
    }

    public ChangeGeneralInfoRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
