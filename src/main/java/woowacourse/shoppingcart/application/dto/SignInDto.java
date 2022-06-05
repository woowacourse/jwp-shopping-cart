package woowacourse.shoppingcart.application.dto;

import woowacourse.auth.dto.TokenRequest;

public class SignInDto {

    private String email;
    private String password;

    private SignInDto() {
    }

    public SignInDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static SignInDto fromTokenRequest(final TokenRequest tokenRequest) {
        return new SignInDto(tokenRequest.getEmail(), tokenRequest.getPassword());
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}

