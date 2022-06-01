package woowacourse.auth.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class TokenRequest {

    private final String account;
    private final String password;

    @JsonCreator
    public TokenRequest(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }
}
