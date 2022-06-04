package woowacourse.shoppingcart.application.dto;

public class CustomerUpdateRequest {

    private String nickname;

    public CustomerUpdateRequest() {
    }

    public CustomerUpdateRequest(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
