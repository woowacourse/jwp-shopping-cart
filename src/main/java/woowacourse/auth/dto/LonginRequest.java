package woowacourse.auth.dto;

public class LonginRequest {

    private String email;
    private String password;

    public LonginRequest() {

    }

    public LonginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
