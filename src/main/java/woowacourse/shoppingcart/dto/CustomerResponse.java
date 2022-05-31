package woowacourse.shoppingcart.dto;

public class CustomerResponse {

    private final Long id;
    private final String email;
    private final String nickname;

    public CustomerResponse(Long id, String email, String nickname) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }
}
