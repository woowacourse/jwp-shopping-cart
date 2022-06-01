package woowacourse.auth.dto;

public class TokenRequest {

    private String name;
    private String password;

    private TokenRequest() {
    }

    public TokenRequest(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
