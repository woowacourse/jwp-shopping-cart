package woowacourse.shoppingcart.customer.application.dto.request;

import javax.validation.constraints.NotNull;

public class CustomerRegisterRequest {

    @NotNull
    private String email;
    @NotNull
    private String nickname;
    @NotNull
    private String password;

    public CustomerRegisterRequest() {
    }

    public CustomerRegisterRequest(final String email, final String nickname, final String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }
}
