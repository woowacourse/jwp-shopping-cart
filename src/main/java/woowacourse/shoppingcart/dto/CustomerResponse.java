package woowacourse.shoppingcart.dto;

public class CustomerResponse {

    private Long id;
    private String userId;
    private String nickname;

    public CustomerResponse() {
    }

    public CustomerResponse(final Long id, final String userId, final String nickname) {
        this.id = id;
        this.userId = userId;
        this.nickname = nickname;
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getNickname() {
        return nickname;
    }
}
